package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientToolMcp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端 MCP 工具配置 DAO 接口。
 */
@Mapper
public interface IAiClientToolMcpDao {

    /**
     * 新增 AI 客户端 MCP 工具配置记录。
     *
     * @param aiClientToolMcp AI 客户端 MCP 工具配置对象
     */
    void insert(AiClientToolMcp aiClientToolMcp);

    /**
     * 根据主键 ID 更新 AI 客户端 MCP 工具配置记录。
     *
     * @param aiClientToolMcp AI 客户端 MCP 工具配置对象
     */
    void updateById(AiClientToolMcp aiClientToolMcp);

    /**
     * 根据 MCP ID 更新 AI 客户端 MCP 工具配置记录。
     *
     * @param aiClientToolMcp AI 客户端 MCP 工具配置对象
     */
    void updateByMcpId(AiClientToolMcp aiClientToolMcp);

    /**
     * 根据主键 ID 删除 AI 客户端 MCP 工具配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据 MCP ID 删除 AI 客户端 MCP 工具配置记录。
     *
     * @param mcpId MCP ID
     */
    void deleteByMcpId(String mcpId);

    /**
     * 根据主键 ID 查询 AI 客户端 MCP 工具配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端 MCP 工具配置对象
     */
    AiClientToolMcp queryById(Long id);

    /**
     * 根据 MCP ID 查询 AI 客户端 MCP 工具配置记录。
     *
     * @param mcpId MCP ID
     * @return AI 客户端 MCP 工具配置对象
     */
    AiClientToolMcp queryByMcpId(String mcpId);

    /**
     * 根据状态查询 AI 客户端 MCP 工具配置记录。
     *
     * @param status 状态
     * @return AI 客户端 MCP 工具配置列表
     */
    List<AiClientToolMcp> queryByStatus(Integer status);

    /**
     * 根据传输类型查询 AI 客户端 MCP 工具配置记录。
     *
     * @param transportType 传输类型
     * @return AI 客户端 MCP 工具配置列表
     */
    List<AiClientToolMcp> queryByTransportType(String transportType);

    /**
     * 查询全部启用状态的 AI 客户端 MCP 工具配置记录。
     *
     * @return AI 客户端 MCP 工具配置列表
     */
    List<AiClientToolMcp> queryEnabledMcps();

    /**
     * 查询全部 AI 客户端 MCP 工具配置记录。
     *
     * @return AI 客户端 MCP 工具配置列表
     */
    List<AiClientToolMcp> queryAll();

}
