package com.beaker.domain.agent.service.armory.business.data.impl;

import com.beaker.domain.agent.adapter.repository.IAgentRepository;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.*;
import com.beaker.domain.agent.service.armory.business.data.ILoadDataStrategy;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author beaker
 * @Date 2026/3/22 14:02
 * @Description 以客户端串联加载配置数据
 */
@Service
@Slf4j
public class AiClientLoadDataStrategy implements ILoadDataStrategy {

    @Resource
    private IAgentRepository repository;
    @Resource
    protected ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        List<String> clientIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", clientIdList);
            return repository.queryAiClientApiVOListByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientModelVO>> aiClientModelListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", clientIdList);
            return repository.AiClientModelVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientToolMcpVO>> aiClientToolMcpListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_tool_mcp) {}", clientIdList);
            return repository.AiClientToolMcpVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientSystemPromptVO>> aiClientSystemPromptListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_system_prompt) {}", clientIdList);
            return repository.AiClientSystemPromptVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientAdvisorVO>> aiClientAdvisorListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_advisor) {}", clientIdList);
            return repository.AiClientAdvisorVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientVO>> aiClientListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client) {}", clientIdList);
            return repository.AiClientVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture.allOf(aiClientApiListFuture, aiClientToolMcpListFuture, aiClientModelListFuture).thenRun(() -> {
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_API.getDataName(), aiClientApiListFuture.join());
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_MODEL.getDataName(), aiClientModelListFuture.join());
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_SYSTEM_PROMPT.getDataName(), aiClientSystemPromptListFuture.join());
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_TOOL_MCP.getDataName(), aiClientToolMcpListFuture.join());
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT_ADVISOR.getLoadDataStrategy(), aiClientAdvisorListFuture.join());
            dynamicContext.setValue(AiAgentEnumVO.AI_CLIENT.getLoadDataStrategy(), aiClientListFuture.join());
        }).join();
    }

}
