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
     * 根据主键 ID 更新 AI 客户端模型配置记录。
     *
     * @param aiClientModel AI 客户端模型配置对象
     */
    void updateById(AiClientModel aiClientModel);

    /**
     * 根据模型 ID 更新 AI 客户端模型配置记录。
     *
     * @param aiClientModel AI 客户端模型配置对象
     */
    void updateByModelId(AiClientModel aiClientModel);

    /**
     * 根据主键 ID 删除 AI 客户端模型配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据模型 ID 删除 AI 客户端模型配置记录。
     *
     * @param modelId 模型 ID
     */
    void deleteByModelId(String modelId);

    /**
     * 根据主键 ID 查询 AI 客户端模型配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端模型配置对象
     */
    AiClientModel queryById(Long id);

    /**
     * 根据模型 ID 查询 AI 客户端模型配置记录。
     *
     * @param modelId 模型 ID
     * @return AI 客户端模型配置对象
     */
    AiClientModel queryByModelId(String modelId);

    /**
     * 根据 API ID 查询 AI 客户端模型配置记录。
     *
     * @param apiId API ID
     * @return AI 客户端模型配置列表
     */
    List<AiClientModel> queryByApiId(String apiId);

    /**
     * 根据模型类型查询 AI 客户端模型配置记录。
     *
     * @param modelType 模型类型
     * @return AI 客户端模型配置列表
     */
    List<AiClientModel> queryByModelType(String modelType);

    /**
     * 查询全部启用状态的 AI 客户端模型配置记录。
     *
     * @return AI 客户端模型配置列表
     */
    List<AiClientModel> queryEnabledModels();

    /**
     * 查询全部 AI 客户端模型配置记录。
     *
     * @return AI 客户端模型配置列表
     */
    List<AiClientModel> queryAll();

}
