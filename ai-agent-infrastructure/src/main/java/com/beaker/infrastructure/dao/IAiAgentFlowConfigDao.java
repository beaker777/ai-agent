package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiAgentFlowConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI 智能体流程配置 DAO 接口。
 */
@Mapper
public interface IAiAgentFlowConfigDao {

    /**
     * 新增 AI 智能体流程配置记录。
     *
     * @param aiAgentFlowConfig AI 智能体流程配置对象
     */
    void insert(AiAgentFlowConfig aiAgentFlowConfig);

    /**
     * 根据主键 ID 更新 AI 智能体流程配置记录。
     *
     * @param aiAgentFlowConfig AI 智能体流程配置对象
     */
    void updateById(AiAgentFlowConfig aiAgentFlowConfig);

    /**
     * 根据主键 ID 删除 AI 智能体流程配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据智能体 ID 删除 AI 智能体流程配置记录。
     *
     * @param agentId 智能体 ID
     */
    void deleteByAgentId(Long agentId);

    /**
     * 根据主键ID查询 AI 智能体流程配置记录。
     *
     * @param id 主键ID
     * @return AI 智能体流程配置对象
     */
    AiAgentFlowConfig queryById(Long id);

    /**
     * 根据智能体 ID 查询 AI 智能体流程配置记录。
     *
     * @param agentId 智能体 ID
     * @return AI 智能体流程配置列表
     */
    List<AiAgentFlowConfig> queryByAgentId(String agentId);

    /**
     * 根据客户端 ID 查询 AI 智能体流程配置记录。
     *
     * @param clientId 客户端 ID
     * @return AI 智能体流程配置列表
     */
    List<AiAgentFlowConfig> queryByClientId(Long clientId);

    /**
     * 根据智能体 ID 和客户端 ID 查询 AI 智能体流程配置记录。
     *
     * @param agentId 智能体 ID
     * @param clientId 客户端 ID
     * @return AI 智能体流程配置对象
     */
    AiAgentFlowConfig queryByAgentIdAndClientId(@Param("agentId") Long agentId, @Param("clientId") Long clientId);

    /**
     * 查询全部 AI 智能体流程配置记录。
     *
     * @return AI 智能体流程配置列表
     */
    List<AiAgentFlowConfig> queryAll();

}
