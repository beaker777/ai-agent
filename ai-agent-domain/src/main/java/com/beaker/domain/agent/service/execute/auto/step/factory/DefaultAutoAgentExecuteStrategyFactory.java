package com.beaker.domain.agent.service.execute.auto.step.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import com.beaker.domain.agent.model.valobj.AiAgentClientFlowConfigVO;
import com.beaker.domain.agent.service.execute.auto.step.RootNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author beaker
 * @Date 2026/3/26 18:12
 * @Description 工厂类
 */
@Service
public class DefaultAutoAgentExecuteStrategyFactory {

    private final RootNode rootNode;

    public DefaultAutoAgentExecuteStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<ExecuteCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext, String> executeStrategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        // 任务当前执行步骤
        private int step = 1;

        // 任务最大执行步骤
        private int maxStep = 1;

        // 执行任务上下文
        private StringBuilder executionHistory;

        // 当前执行任务
        private String currentTask;

        // 任务完成状态
        private boolean isCompleted;

        // agent 配置的 client
        private Map<String, AiAgentClientFlowConfigVO> aiAgentClientFlowConfigVOMap;

        // 数据对象
        private Map<String, Object> dataObjects = new HashMap<>();

        public <T> void setValue(String key, T value) {
            dataObjects.put(key, value);
        }

        public <T> T getValue(String key) {
            return (T) dataObjects.get(key);
        }
    }
}
