package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientAdvisor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端顾问配置 DAO 接口。
 */
@Mapper
public interface IAiClientAdvisorDao {

    /**
     * 新增 AI 客户端顾问配置记录。
     *
     * @param aiClientAdvisor AI 客户端顾问配置对象
     */
    void insert(AiClientAdvisor aiClientAdvisor);

    /**
     * 更新 AI 客户端顾问配置记录。
     *
     * @param aiClientAdvisor AI 客户端顾问配置对象
     */
    void update(AiClientAdvisor aiClientAdvisor);

    /**
     * 根据主键ID查询 AI 客户端顾问配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端顾问配置对象
     */
    AiClientAdvisor queryById(Long id);

    /**
     * 查询全部 AI 客户端顾问配置记录。
     *
     * @return AI 客户端顾问配置列表
     */
    List<AiClientAdvisor> queryAll();

}
