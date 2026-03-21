package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiAgent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 智能体配置 DAO 接口。
 */
@Mapper
public interface IAiAgentDao {

    /**
     * 新增 AI 智能体配置记录。
     *
     * @param aiAgent AI 智能体配置对象
     */
    void insert(AiAgent aiAgent);

    /**
     * 更新 AI 智能体配置记录。
     *
     * @param aiAgent AI 智能体配置对象
     */
    void update(AiAgent aiAgent);

    /**
     * 根据主键ID查询 AI 智能体配置记录。
     *
     * @param id 主键ID
     * @return AI 智能体配置对象
     */
    AiAgent queryById(Long id);

    /**
     * 查询全部 AI 智能体配置记录。
     *
     * @return AI 智能体配置列表
     */
    List<AiAgent> queryAll();

}
