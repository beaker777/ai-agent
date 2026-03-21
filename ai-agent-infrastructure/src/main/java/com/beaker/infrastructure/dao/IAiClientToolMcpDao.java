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
     * 更新 AI 客户端 MCP 工具配置记录。
     *
     * @param aiClientToolMcp AI 客户端 MCP 工具配置对象
     */
    void update(AiClientToolMcp aiClientToolMcp);

    /**
     * 根据主键ID查询 AI 客户端 MCP 工具配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端 MCP 工具配置对象
     */
    AiClientToolMcp queryById(Long id);

    /**
     * 查询全部 AI 客户端 MCP 工具配置记录。
     *
     * @return AI 客户端 MCP 工具配置列表
     */
    List<AiClientToolMcp> queryAll();

}
