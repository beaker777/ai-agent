package com.beaker.domain.agent.service.execute;

import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @Author beaker
 * @Date 2026/3/26 16:42
 * @Description 执行策略接口
 */
public interface IExecuteStrategy {

    void execute(ExecuteCommandEntity requestParameter, ResponseBodyEmitter emitter) throws Exception;
}
