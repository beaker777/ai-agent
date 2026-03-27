package com.beaker.domain.agent.service.execute.auto;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import com.beaker.domain.agent.service.execute.IExecuteStrategy;
import com.beaker.domain.agent.service.execute.auto.step.factory.DefaultAutoAgentExecuteStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author beaker
 * @Date 2026/3/26 18:10
 * @Description 自动执行策略
 */
@Service
@Slf4j
public class AutoAgentExecuteStrategy implements IExecuteStrategy {

    @Resource
    private DefaultAutoAgentExecuteStrategyFactory defaultAutoAgentExecuteStrategyFactory;

    @Override
    public void execute(ExecuteCommandEntity requestParameter) throws Exception {
        StrategyHandler<ExecuteCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext, String> executeHandler
                = defaultAutoAgentExecuteStrategyFactory.executeStrategyHandler();

        String apply = executeHandler.apply(requestParameter, new DefaultAutoAgentExecuteStrategyFactory.DynamicContext());
        log.info("测试结果: {}", apply);
    }
}
