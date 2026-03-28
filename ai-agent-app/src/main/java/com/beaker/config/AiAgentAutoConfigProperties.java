package com.beaker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author beaker
 * @Date 2026/3/27 18:32
 * @Description AiAgent 自动装配配置
 */
@Data
@ConfigurationProperties(prefix = "spring.ai.agent.auto-config")
public class AiAgentAutoConfigProperties {

    /**
     * 是否使用自动装配
     */
    private boolean enable = false;

    /**
     * 需要自动装配的 client id
     */
    private List<String> clientIds;
}
