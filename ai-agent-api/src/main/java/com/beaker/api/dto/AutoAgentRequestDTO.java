package com.beaker.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author beaker
 * @Date 2026/3/27 15:26
 * @Description Auto Agent 请求 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoAgentRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * AI 智能体 ID
     */
    private String aiAgentId;

    /**
     * 用户消息
     */
    private String message;

    /**
     * 会话 ID
     */
    private String sessionId;

    /**
     * 最大执行步数
     */
    private Integer maxStep;
}
