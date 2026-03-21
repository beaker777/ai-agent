package com.beaker.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AI 客户端 MCP 工具配置持久化对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiClientToolMcp {

    /** 主键ID。 */
    private Long id;
    /** MCP名称。 */
    private String mcpId;
    /** MCP名称。 */
    private String mcpName;
    /** 传输类型(sse/stdio)。 */
    private String transportType;
    /** 传输配置(sse/stdio)。 */
    private String transportConfig;
    /** 请求超时时间(分钟)。 */
    private Integer requestTimeout;
    /** 状态(0:禁用,1:启用)。 */
    private Integer status;
    /** 创建时间。 */
    private LocalDateTime createTime;
    /** 更新时间。 */
    private LocalDateTime updateTime;

}
