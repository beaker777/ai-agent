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
     * 根据主键 ID 更新 AI 客户端顾问配置记录。
     *
     * @param aiClientAdvisor AI 客户端顾问配置对象
     */
    void updateById(AiClientAdvisor aiClientAdvisor);

    /**
     * 根据顾问 ID 更新 AI 客户端顾问配置记录。
     *
     * @param aiClientAdvisor AI 客户端顾问配置对象
     */
    void updateByAdvisorId(AiClientAdvisor aiClientAdvisor);

    /**
     * 根据主键 ID 删除 AI 客户端顾问配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据顾问 ID 删除 AI 客户端顾问配置记录。
     *
     * @param advisorId 顾问 ID
     */
    void deleteByAdvisorId(String advisorId);

    /**
     * 根据主键 ID 查询 AI 客户端顾问配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端顾问配置对象
     */
    AiClientAdvisor queryById(Long id);

    /**
     * 根据顾问 ID 查询 AI 客户端顾问配置记录。
     *
     * @param advisorId 顾问 ID
     * @return AI 客户端顾问配置对象
     */
    AiClientAdvisor queryByAdvisorId(String advisorId);

    /**
     * 根据状态查询 AI 客户端顾问配置记录。
     *
     * @param status 状态
     * @return AI 客户端顾问配置列表
     */
    List<AiClientAdvisor> queryByStatus(Integer status);

    /**
     * 根据顾问类型查询 AI 客户端顾问配置记录。
     *
     * @param advisorType 顾问类型
     * @return AI 客户端顾问配置列表
     */
    List<AiClientAdvisor> queryByAdvisorType(String advisorType);

    /**
     * 查询全部 AI 客户端顾问配置记录。
     *
     * @return AI 客户端顾问配置列表
     */
    List<AiClientAdvisor> queryAll();

}
