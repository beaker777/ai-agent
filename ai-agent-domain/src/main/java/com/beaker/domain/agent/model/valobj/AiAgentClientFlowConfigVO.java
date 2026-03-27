package com.beaker.domain.agent.model.valobj;

import lombok.Builder;
import lombok.Data;

/**
 * @Author beaker
 * @Date 2026/3/26 17:53
 * @Description 客户端配置
 */
@Data
@Builder
public class AiAgentClientFlowConfigVO {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端枚举
     */
    private String clientType;

    /**
     * 序列号(执行顺序)
     */
    private Integer sequence;

}
