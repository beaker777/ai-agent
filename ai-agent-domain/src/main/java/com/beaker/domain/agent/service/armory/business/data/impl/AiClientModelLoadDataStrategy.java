package com.beaker.domain.agent.service.armory.business.data.impl;

import com.beaker.domain.agent.adapter.repository.IAgentRepository;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.AiClientApiVO;
import com.beaker.domain.agent.model.valobj.AiClientModelVO;
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
 * @Description 以客户端对话模型, 加载数据策略
 */
@Service
@Slf4j
public class AiClientModelLoadDataStrategy implements ILoadDataStrategy {

    @Resource
    private IAgentRepository agentRepository;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        List<String> modelIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiList = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", modelIdList);
            return agentRepository.queryAiClientApiVOListByModelIds(modelIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientModelVO>> aiClientModelList = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", modelIdList);
            return agentRepository.AiClientModelVOByModelIds(modelIdList);
        }, threadPoolExecutor);
    }
}
