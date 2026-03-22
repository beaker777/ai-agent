package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端 API 配置 DAO 接口。
 */
@Mapper
public interface IAiClientApiDao {

    /**
     * 新增 AI 客户端 API 配置记录。
     *
     * @param aiClientApi AI 客户端 API 配置对象
     */
    void insert(AiClientApi aiClientApi);

    /**
     * 根据主键 ID 更新 AI 客户端 API 配置记录。
     *
     * @param aiClientApi AI 客户端 API 配置对象
     */
    void updateById(AiClientApi aiClientApi);

    /**
     * 根据 API ID 更新 AI 客户端 API 配置记录。
     *
     * @param aiClientApi AI 客户端 API 配置对象
     */
    void updateByApiId(AiClientApi aiClientApi);

    /**
     * 根据主键 ID 删除 AI 客户端 API 配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据 API ID 删除 AI 客户端 API 配置记录。
     *
     * @param apiId API ID
     */
    void deleteByApiId(String apiId);

    /**
     * 根据主键 ID 查询 AI 客户端 API 配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端 API 配置对象
     */
    AiClientApi queryById(Long id);

    /**
     * 根据 API ID 查询 AI 客户端 API 配置记录。
     *
     * @param apiId API ID
     * @return AI 客户端 API 配置对象
     */
    AiClientApi queryByApiId(String apiId);

    /**
     * 查询全部启用状态的 AI 客户端 API 配置记录。
     *
     * @return AI 客户端 API 配置列表
     */
    List<AiClientApi> queryEnabledApis();

    /**
     * 查询全部 AI 客户端 API 配置记录。
     *
     * @return AI 客户端 API 配置列表
     */
    List<AiClientApi> queryAll();

}
