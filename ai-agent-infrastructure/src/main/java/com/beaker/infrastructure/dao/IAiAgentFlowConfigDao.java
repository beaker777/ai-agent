package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiAgentFlowConfig;
import org.apache.ibatis.annotations.Mapper;

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
     * 更新 AI 智能体流程配置记录。
     *
     * @param aiAgentFlowConfig AI 智能体流程配置对象
     */
    void update(AiAgentFlowConfig aiAgentFlowConfig);

    /**
     * 根据主键ID查询 AI 智能体流程配置记录。
     *
     * @param id 主键ID
     * @return AI 智能体流程配置对象
     */
    AiAgentFlowConfig queryById(Long id);

    /**
     * 查询全部 AI 智能体流程配置记录。
     *
     * @return AI 智能体流程配置列表
     */
    List<AiAgentFlowConfig> queryAll();

}
