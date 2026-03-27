package com.beaker.domain.agent.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author beaker
 * @Date 2026/3/26 16:44
 * @Description 执行命令实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCommandEntity {

    /**
     * agent id
     */
    private String agentId;

    /**
     * 信息
     */
    private String message;

    /**
     * 会话 id
     */
    private String sessionId;

    /**
     * 最大步数
     */
    private Integer maxStep;
}
