package com.beaker.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.beaker.domain.agent.model.valobj.AiClientModelVO;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpSyncClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author beaker
 * @Date 2026/3/22 20:52
 * @Description model 节点数据加载
 */
@Service
@Slf4j
public class AiClientModelNode extends AbstractArmorySupport{

    @Resource
    private AiClientAdvisorNode aiClientAdvisorNode;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建, Model 节点 {}", JSON.toJSONString(requestParameter));

        List<AiClientModelVO> aiClientModeList = dynamicContext.getValue(dataName());

        if (aiClientModeList == null || aiClientModeList.isEmpty()) {
            log.warn("没有要被初始化的 ai client model 节点");
            return router(requestParameter, dynamicContext);
        }

        for (AiClientModelVO aiClientModelVO : aiClientModeList) {
            // 获取当前模型相关的 api bean
            OpenAiApi openAiApi = getBean(AiAgentEnumVO.AI_CLIENT_API.getBeanName(aiClientModelVO.getApiId()));
            if (openAiApi == null) {
                throw new RuntimeException("model's api is null");
            }

            // 获取当前模型相关的 mcp bean
            List<McpSyncClient> mcpSyncClients = new ArrayList<>();
            for (String toolMcpId : aiClientModelVO.getToolMcpIds()) {
                McpSyncClient mcpSyncClient = getBean(AiAgentEnumVO.AI_CLIENT_TOOL_MCP.getBeanName(toolMcpId));
                mcpSyncClients.add(mcpSyncClient);
            }

            // 实例化对话模型
            OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                    .openAiApi(openAiApi)
                    .defaultOptions(
                            OpenAiChatOptions.builder()
                                    .model(aiClientModelVO.getModelName())
                                    .toolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients).getToolCallbacks())
                                    .build()
                    )
                    .build();

            registerBean(beanName(aiClientModelVO.getModelId()), OpenAiChatModel.class, openAiChatModel);
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientAdvisorNode;
    }

    @Override
    protected String beanName(String id) {
        return AiAgentEnumVO.AI_CLIENT_MODEL.getBeanName(id);
    }

    @Override
    protected String dataName() {
        return AiAgentEnumVO.AI_CLIENT_MODEL.getDataName();
    }
}
