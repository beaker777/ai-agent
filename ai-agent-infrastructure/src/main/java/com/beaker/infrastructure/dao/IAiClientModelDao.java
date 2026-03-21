package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端模型配置 DAO 接口。
 */
@Mapper
public interface IAiClientModelDao {

    /**
     * 新增 AI 客户端模型配置记录。
     *
     * @param aiClientModel AI 客户端模型配置对象
     */
    void insert(AiClientModel aiClientModel);

    /**
     * 更新 AI 客户端模型配置记录。
     *
     * @param aiClientModel AI 客户端模型配置对象
     */
    void update(AiClientModel aiClientModel);

    /**
     * 根据主键ID查询 AI 客户端模型配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端模型配置对象
     */
    AiClientModel queryById(Long id);

    /**
     * 查询全部 AI 客户端模型配置记录。
     *
     * @return AI 客户端模型配置列表
     */
    List<AiClientModel> queryAll();

}
