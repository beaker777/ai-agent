package com.beaker.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.AiAgentEnumVO;
import com.beaker.domain.agent.model.valobj.AiClientSystemPromptVO;
import com.beaker.domain.agent.model.valobj.AiClientVO;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author beaker
 * @Date 2026/3/23 20:34
 * @Description 客户端节点
 */
@Service
@Slf4j
public class AiClientNode extends AbstractArmorySupport{
    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建, Client 节点 {}", requestParameter);

        List<AiClientVO> aiClientList = dynamicContext.getValue(dataName());

        if (aiClientList == null || aiClientList.isEmpty()) {
            log.warn("当前没有需要被初始化的 ai client 节点");
            return router(requestParameter, dynamicContext);
        }

        Map<String, AiClientSystemPromptVO> systemPrompt = dynamicContext.getValue(AiAgentEnumVO.AI_CLIENT_SYSTEM_PROMPT.getDataName());

        for (AiClientVO aiClientVO : aiClientList) {
            // 1. 预设话术
            StringBuilder defaultSystem = new StringBuilder("Ai 智能体 \r\n");
            List<String> promptIdList = aiClientVO.getPromptIdList();
            for (String promptId : promptIdList) {
                AiClientSystemPromptVO aiClientSystemPromptVO = systemPrompt.get(promptId);
                defaultSystem.append(aiClientSystemPromptVO.getPromptContent());
            }

            // 2. 对话模型
            OpenAiChatModel chatModel = getBean(AiAgentEnumVO.AI_CLIENT_MODEL.getBeanName(aiClientVO.getModelId()));

            // 3. MCP 服务
            List<McpSyncClient> mcpSyncClients = new ArrayList<>();
            List<String> mcpIdList = aiClientVO.getMcpIdList();
            for (String mcpId : mcpIdList) {
                mcpSyncClients.add(getBean(AiAgentEnumVO.AI_CLIENT_TOOL_MCP.getBeanName(mcpId)));
            }

            // 4. advisor 顾问
            List<Advisor> advisors = new ArrayList<>();
            List<String> advisorIdList = aiClientVO.getAdvisorIdList();
            for (String advisorId : advisorIdList) {
                advisors.add(getBean(AiAgentEnumVO.AI_CLIENT_ADVISOR.getBeanName(advisorId)));
            }

            // 5. 装配对话客户端
            ChatClient chatClient = ChatClient.builder(chatModel)
                    .defaultSystem(defaultSystem.toString())
                    .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients.toArray(new McpSyncClient[]{})))
                    .defaultAdvisors(advisors)
                    .build();

            // 6. 注册为 bean
            registerBean(beanName(aiClientVO.getClientId()), ChatClient.class, chatClient);
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }

    @Override
    protected String beanName(String id) {
        return AiAgentEnumVO.AI_CLIENT.getBeanName(id);
    }

    @Override
    protected String dataName() {
        return AiAgentEnumVO.AI_CLIENT.getDataName();
    }
}
