package com.beaker.domain.agent.service.execute.auto;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.beaker.domain.agent.model.entity.AutoAgentExecuteResultEntity;
import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import com.beaker.domain.agent.service.execute.IExecuteStrategy;
import com.beaker.domain.agent.service.execute.auto.step.factory.DefaultAutoAgentExecuteStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @Author beaker
 * @Date 2026/3/26 18:10
 * @Description 自动执行策略
 */
@Service("autoAgentExecuteStrategy")
@Slf4j
public class AutoAgentExecuteStrategy implements IExecuteStrategy {

    @Resource
    private DefaultAutoAgentExecuteStrategyFactory defaultAutoAgentExecuteStrategyFactory;

    @Override
    public void execute(ExecuteCommandEntity requestParameter, ResponseBodyEmitter emitter) throws Exception {
        StrategyHandler<ExecuteCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext, String> executeHandler
                = defaultAutoAgentExecuteStrategyFactory.executeStrategyHandler();

        // 创建动态上下文初始化必要字段
        DefaultAutoAgentExecuteStrategyFactory.DynamicContext dynamicContext = new DefaultAutoAgentExecuteStrategyFactory.DynamicContext();
        dynamicContext.setMaxStep(requestParameter.getMaxStep() != null ? requestParameter.getMaxStep() : 3);
        dynamicContext.setExecutionHistory(new StringBuilder());
        dynamicContext.setCurrentTask(requestParameter.getMessage());
        dynamicContext.setValue("emitter", emitter);

        String apply = executeHandler.apply(requestParameter, dynamicContext);
        log.info("运行结果: {}", apply);

        // 发送完成标志
        try {
            AutoAgentExecuteResultEntity completeResult = AutoAgentExecuteResultEntity.createCompleteResult(requestParameter.getSessionId());
            // 发送 SSE 格式的数据
            String sseData = "data: " + JSON.toJSONString(completeResult);
            emitter.send(sseData);
        } catch (Exception e) {
            log.error("发送完成标志失败: {}", e.getMessage());
        }
    }
}
