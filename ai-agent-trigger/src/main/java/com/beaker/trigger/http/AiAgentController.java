package com.beaker.trigger.http;

import com.alibaba.fastjson.JSON;
import com.beaker.api.IAiAgentService;
import com.beaker.api.dto.AutoAgentRequestDTO;
import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import com.beaker.domain.agent.service.execute.IExecuteStrategy;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author beaker
 * @Date 2026/3/27 15:37
 * @Description AI 智能体
 */
@RestController
@RequestMapping("/api/v1/agent")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@Slf4j
public class AiAgentController implements IAiAgentService {

    @Resource(name = "autoAgentExecuteStrategy")
    private IExecuteStrategy autoAgentExecuteStrategy;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @RequestMapping(value = "auto_agent", method = RequestMethod.POST)
    @Override
    public ResponseBodyEmitter autoAgent(@RequestBody AutoAgentRequestDTO request, HttpServletResponse response) {
        log.info("AutoAgent 流式请求执行开始, 请求信息: {}", JSON.toJSONString(request));

        try {
            // 设置 SSE 响应头
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Connection", "keep-alive");

            // 1. 创建流式输出对象
            ResponseBodyEmitter emitter = new ResponseBodyEmitter(Long.MAX_VALUE);

            // 2. 构建执行命令实体
            ExecuteCommandEntity executeCommandEntity = ExecuteCommandEntity.builder()
                    .agentId(request.getAiAgentId())
                    .message(request.getMessage())
                    .sessionId(request.getSessionId())
                    .maxStep(request.getMaxStep())
                    .build();

            // 3. 异步执行 AutoAgent
            threadPoolExecutor.execute(() -> {
                try {
                    autoAgentExecuteStrategy.execute(executeCommandEntity, emitter);
                } catch (Exception e) {
                    log.error("AutoAgent 执行异常: {}", e.getMessage());

                    try {
                        emitter.send("执行异常" + e.getMessage());
                    } catch (Exception ex) {
                        log.error("发送异常信息失败: {}", ex.getMessage(), e);
                    }
                } finally {
                    try {
                        emitter.complete();
                    } catch (Exception e) {
                        log.error("完成流式输出失败: {}", e.getMessage());
                    }
                }
            });

            return emitter;
        } catch (Exception e) {
            log.error("AutoAgent 请求处理异常: {}", e.getMessage());

            ResponseBodyEmitter errorEmitter = new ResponseBodyEmitter();
            try {
                errorEmitter.send("请求处理异常" + e.getMessage());
                errorEmitter.complete();
            } catch (Exception ex) {
                log.error("发送异常信息失败: {}", ex.getMessage());
            }

            return errorEmitter;
        }
    }
}
