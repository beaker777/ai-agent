package com.beaker.infrastructure.adapter.repository;

import com.alibaba.fastjson.JSON;
import com.beaker.domain.agent.adapter.repository.IAgentRepository;
import com.beaker.domain.agent.model.valobj.*;
import com.beaker.infrastructure.dao.*;
import com.beaker.infrastructure.dao.po.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.beaker.domain.agent.model.valobj.AiAgentEnumVO.AI_CLIENT;
import static com.beaker.domain.agent.model.valobj.AiAgentEnumVO.AI_CLIENT_MODEL;

/**
 * @Author beaker
 * @Date 2026/3/22 15:40
 * @Description 仓储服务
 */
@Repository
@Slf4j
public class AgentRepository implements IAgentRepository {

    @Resource
    private IAiClientConfigDao aiClientConfigDao;

    @Resource
    private IAiClientModelDao aiClientModelDao;

    @Resource
    private IAiClientApiDao aiClientApiDao;

    @Resource
    private IAiClientToolMcpDao aiClientToolMcpDao;

    @Resource
    private IAiClientSystemPromptDao aiClientSystemPromptDao;

    @Resource
    private IAiClientAdvisorDao aiClientAdvisorDao;

    @Resource
    private IAiClientDao aiClientDao;

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientApiVO> result = new ArrayList<>();

        for (String clientId : clientIdList) {
            // 1. 根据 client id 获取对应的 config
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                if (AI_CLIENT_MODEL.getCode().equals(config.getTargetType()) && config.getStatus() == 1) {
                    // 2. 获取 client 对应的模型 id
                    String modelId = config.getTargetId();

                    // 3. 根据 model id 查询模型配置, 获取 api id
                    AiClientModel model = aiClientModelDao.queryByModelId(modelId);
                    if (model != null && model.getStatus() == 1) {
                        String apiId = model.getApiId();

                        // 4. 通过 api id 查询 api 配置
                        AiClientApi apiConfig = aiClientApiDao.queryByApiId(apiId);
                        if (apiConfig != null && apiConfig.getStatus() == 1) {
                            // 5. 转换为 api 对象
                            AiClientApiVO apiVO = AiClientApiVO.builder()
                                    .apiId(apiConfig.getApiId())
                                    .baseUrl(apiConfig.getBaseUrl())
                                    .apiKey(apiConfig.getApiKey())
                                    .completionsPath(apiConfig.getCompletionsPath())
                                    .embeddingsPath(apiConfig.getEmbeddingsPath())
                                    .build();

                            // 6. 避免重复添加
                            if (result.stream().noneMatch(vo -> vo.getApiId().equals(apiVO.getApiId()))) {
                                result.add(apiVO);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientModelVO> result = new ArrayList<>();

        for (String clientId : clientIdList) {
            // 根据 client id 查询对应的 config
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                if (AI_CLIENT_MODEL.getCode().equals(config.getTargetType()) && config.getStatus() == 1) {
                    String modelId = config.getTargetId();

                    // 根据 model id 获取模型配置
                    AiClientModel model = aiClientModelDao.queryByModelId(modelId);
                    if (model != null && model.getStatus() == 1) {
                        AiClientModelVO modelVO = AiClientModelVO.builder()
                                .modelId(model.getModelId())
                                .apiId(model.getApiId())
                                .modelName(model.getModelName())
                                .modelType(model.getModelType())
                                .build();

                        // 避免重复加入
                        if (result.stream().noneMatch(vo -> vo.getModelId().equals(modelVO.getModelId()))) {
                            result.add(modelVO);
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientToolMcpVO> AiClientToolMcpVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientToolMcpVO> result = new ArrayList<>();
        Set<String> processedMcpIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 根据 client id 查询对应的 config
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                if ("tool_mcp".equals(config.getTargetType()) && config.getStatus() == 1) {
                    String mcpId = config.getTargetId();

                    // 避免重复处理相同的 mcpId
                    if (processedMcpIds.contains(mcpId)) {
                        continue;
                    }
                    processedMcpIds.add(mcpId);

                    // 根据 mcp id 查询 mcp 配置
                    AiClientToolMcp toolMcp = aiClientToolMcpDao.queryByMcpId(mcpId);
                    if (toolMcp != null && toolMcp.getStatus() == 1) {
                        // 转换为 VO 对象
                        AiClientToolMcpVO toolMcpVO = AiClientToolMcpVO.builder()
                                .mcpId(toolMcp.getMcpId())
                                .mcpName(toolMcp.getMcpName())
                                .transportType(toolMcp.getTransportType())
                                .transportConfig(toolMcp.getTransportConfig())
                                .requestTimeout(toolMcp.getRequestTimeout())
                                .build();

                        result.add(toolMcpVO);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientSystemPromptVO> AiClientSystemPromptVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientSystemPromptVO> result = new ArrayList<>();
        Set<String> processedPromptIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 根据 client id 查询对应的 config
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                if ("prompt".equals(config.getTargetType()) && config.getStatus() == 1) {
                    String promptId = config.getTargetId();

                    // 避免重复操作
                    if (processedPromptIds.contains(promptId)) {
                        continue;
                    }
                    processedPromptIds.add(promptId);

                    // 根据 prompt id 查询 prompt 配置
                    AiClientSystemPrompt systemPrompt = aiClientSystemPromptDao.queryByPromptId(promptId);
                    if (systemPrompt != null && systemPrompt.getStatus() == 1) {
                        // 转换为 VO 对象
                        AiClientSystemPromptVO promptVO = AiClientSystemPromptVO.builder()
                                .promptId(systemPrompt.getPromptId())
                                .promptName(systemPrompt.getPromptName())
                                .promptContent(systemPrompt.getPromptContent())
                                .description(systemPrompt.getDescription())
                                .build();

                        result.add(promptVO);
                    }
                }
            }
        }

        return result;
    }


    @Override
    public List<AiClientAdvisorVO> AiClientAdvisorVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientAdvisorVO> result = new ArrayList<>();
        Set<String> processedAdvisorIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 根据 client id 查询对应的 config
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            for (AiClientConfig config : configs) {
                if ("advisor".equals(config.getTargetType()) && config.getStatus() == 1) {
                    String advisorId = config.getTargetId();

                    // 避免重复操作
                    if (processedAdvisorIds.contains(advisorId)) {
                        continue;
                    }
                    processedAdvisorIds.add(advisorId);

                    // 根据 advisor 配置查询信息
                    AiClientAdvisor advisor = aiClientAdvisorDao.queryByAdvisorId(advisorId);
                    if (advisor == null || advisor.getStatus() != 1) {
                        continue;
                    }

                    // 解析 ext_param 中的配置
                    AiClientAdvisorVO.ChatMemory chatMemory = null;
                    AiClientAdvisorVO.RagAnswer ragAnswer = null;

                    String extParam = advisor.getExtParam();
                    if (extParam != null && !extParam.isBlank()) {
                        try {
                            if ("ChatMemory".equals(advisor.getAdvisorType())) {
                                // 解析 chatMemory 配置
                                chatMemory = JSON.parseObject(extParam, AiClientAdvisorVO.ChatMemory.class);
                            } else if ("RagAnswer".equals(advisor.getAdvisorType())) {
                                // 解析 ragAnswer 配置
                                ragAnswer = JSON.parseObject(extParam, AiClientAdvisorVO.RagAnswer.class);
                            }
                        } catch (Exception e) {
                            // 如果失败, 使用默认值 null
                        }
                    }

                    AiClientAdvisorVO advisorVO = AiClientAdvisorVO.builder()
                            .advisorId(advisor.getAdvisorId())
                            .advisorName(advisor.getAdvisorName())
                            .advisorType(advisor.getAdvisorType())
                            .orderNum(advisor.getOrderNum())
                            .ragAnswer(ragAnswer)
                            .chatMemory(chatMemory)
                            .build();

                    result.add(advisorVO);
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientVO> AiClientVOByClientIds(List<String> clientIdList) {
        if (clientIdList == null || clientIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientVO> result = new ArrayList<>();
        Set<String> processedClientIds = new HashSet<>();

        for (String clientId : clientIdList) {
            // 避免重复处理
            if (processedClientIds.contains(clientId)) {
                continue;
            }
            processedClientIds.add(clientId);

            // 根据 client id 查询基本信息
            AiClient aiClient = aiClientDao.queryByClientId(clientId);
            if (aiClient == null || aiClient.getStatus() != 1) {
                continue;
            }

            // 查询配置信息
            List<AiClientConfig> configs = aiClientConfigDao.queryBySourceTypeAndId(AI_CLIENT.getCode(), clientId);

            String modelId = null;
            List<String> promptIdList = new ArrayList<>();
            List<String> mcpIdList = new ArrayList<>();
            List<String> advisorIdList = new ArrayList<>();

            for (AiClientConfig config : configs) {
                if (config.getStatus() != 1) {
                    continue;
                }

                switch (config.getTargetType()) {
                    case "model":
                        modelId = config.getTargetId();
                        break;
                    case "prompt":
                        promptIdList.add(config.getTargetId());
                        break;
                    case "tool_mcp":
                        mcpIdList.add(config.getTargetId());
                        break;
                    case "advisor":
                        advisorIdList.add(config.getTargetId());
                        break;
                }
            }

            AiClientVO aiClientVO = AiClientVO.builder()
                    .clientId(aiClient.getClientId())
                    .clientName(aiClient.getClientName())
                    .description(aiClient.getDescription())
                    .modelId(modelId)
                    .promptIdList(promptIdList)
                    .mcpIdList(mcpIdList)
                    .advisorIdList(advisorIdList)
                    .build();

            result.add(aiClientVO);
        }

        return result;
    }

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByModelIds(List<String> modelIdList) {
        if (modelIdList == null || modelIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientApiVO> result = new ArrayList<>();

        for (String modelId : modelIdList) {
            // 根据 model id 查询模型配置, 获取 api id
            AiClientModel model = aiClientModelDao.queryByModelId(modelId);
            if (model != null && model.getStatus() == 1) {
                String apiId = model.getApiId();

                // 通过 api id 查询 api 配置
                AiClientApi apiConfig = aiClientApiDao.queryByApiId(apiId);
                if (apiConfig != null && apiConfig.getStatus() == 1) {
                    // 转换为 api 对象
                    AiClientApiVO apiVO = AiClientApiVO.builder()
                            .apiId(apiConfig.getApiId())
                            .baseUrl(apiConfig.getBaseUrl())
                            .apiKey(apiConfig.getApiKey())
                            .completionsPath(apiConfig.getCompletionsPath())
                            .embeddingsPath(apiConfig.getEmbeddingsPath())
                            .build();

                    // 避免重复添加
                    if (result.stream().noneMatch(vo -> vo.getApiId().equals(apiVO.getApiId()))) {
                        result.add(apiVO);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<AiClientModelVO> AiClientModelVOByModelIds(List<String> modelIdList) {
        if (modelIdList == null || modelIdList.isEmpty()) {
            return List.of();
        }

        List<AiClientModelVO> result = new ArrayList<>();

        for (String modelId : modelIdList) {
            // 根据 model id 查询模型配置, 获取 api id
            AiClientModel model = aiClientModelDao.queryByModelId(modelId);
            if (model != null && model.getStatus() == 1) {
                // 转换为 VO 对象
                AiClientModelVO modelVO = AiClientModelVO.builder()
                        .modelId(model.getModelId())
                        .apiId(model.getApiId())
                        .modelName(model.getModelName())
                        .modelType(model.getModelType())
                        .build();

                // 避免重复添加
                if (result.stream().noneMatch(vo -> vo.getModelId().equals(modelVO.getModelId()))) {
                    result.add(modelVO);
                }
            }
        }

        return result;
    }
}
