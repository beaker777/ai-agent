package com.beaker.domain.agent.service.armory.factory.element;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author beaker
 * @Date 2026/3/23 20:39
 * @Description rag advisor
 */
public class RagAnswerAdvisor implements BaseAdvisor {

    private final VectorStore vectorStore;
    private final SearchRequest searchRequest;
    private final String userTextAdvise;

    public RagAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest) {
        this.vectorStore = vectorStore;
        this.searchRequest = searchRequest;
        this.userTextAdvise = "\nContext information is below, surrounded by ---------------------\n\n---------------------\n{question_answer_context}\n---------------------\n\nGiven the context and provided history information and not prior knowledge,\nreply to the user comment. If the answer is not in the context, inform\nthe user that you can't answer the question.\n";
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        Map<String, Object> requestContext = chatClientRequest.context();
        Map<String, Object> advisedContext = new HashMap<>(requestContext);

        // 根据 user prompt 构造 searchRequest, topK 在初始化时已经传递
        String userText = chatClientRequest.prompt().getUserMessage().getText();
        Filter.Expression filterExpression = doGetFilterExpression(advisedContext);
        SearchRequest searchRequestToUse = SearchRequest.from(this.searchRequest)
                .query(userText)
                .filterExpression(filterExpression)
                .build();

        // 在 rag 中查询最相似 document 并存储
        List<Document> documents = vectorStore.similaritySearch(searchRequestToUse);
        if (documents == null) {
            documents = Collections.emptyList();
        }
        advisedContext.put("qa_retrieved_documents", documents);

        // 将查询到的 documents 拼接成字符串
        String documentContext = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        // 将拼接好的字符串填充到 prompt
        String advisedUserText = userText + System.lineSeparator() + new PromptTemplate(this.userTextAdvise)
                .render(Map.of("question_answer_context", documentContext));

        // 倒序遍历 message, 找到最新的 user message 修改
        List<Message> originalMessages = chatClientRequest.prompt().getInstructions();
        List<Message> advisedMessages = new ArrayList<>(originalMessages);
        for (int i = advisedMessages.size() - 1; i >= 0; i--) {
            if (advisedMessages.get(i) instanceof UserMessage) {
                advisedMessages.set(i, new UserMessage(advisedUserText));
                break;
            }
        }

        // 将增强后的 client 返回
        return ChatClientRequest.builder()
                .prompt(Prompt.builder().messages(advisedMessages).build())
                .context(advisedContext)
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        ChatResponse.Builder chatResponseBuilder = ChatResponse.builder().from(chatClientResponse.chatResponse());
        chatResponseBuilder.metadata("qa_retrieved_documents", chatClientResponse.context().get("qa_retrieved_documents"));
        ChatResponse chatResponse = chatResponseBuilder.build();

        return ChatClientResponse.builder()
                .chatResponse(chatResponse)
                .context(chatClientResponse.context())
                .build();
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(this.before(chatClientRequest, callAdvisorChain));
        return this.after(chatClientResponse, callAdvisorChain);
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        return BaseAdvisor.super.adviseStream(chatClientRequest, streamAdvisorChain);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    protected Filter.Expression doGetFilterExpression(Map<String, Object> context) {
        /*
          检查上下文中是否存在过滤条件
          如果存在, 检查是否为空
          不为空, 将新地过滤条件返回, 否则使用默认条件
         */
        return context.containsKey("qa_filter_expression")
                && StringUtils.hasText(context.get("qa_filter_expression").toString()) ?
                (new FilterExpressionTextParser()).parse(context.get("qa_filter_expression").toString()) :
                this.searchRequest.getFilterExpression();
    }


}
