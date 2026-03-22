package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI 客户端关联配置 DAO 接口。
 */
@Mapper
public interface IAiClientConfigDao {

    /**
     * 新增 AI 客户端关联配置记录。
     *
     * @param aiClientConfig AI 客户端关联配置对象
     */
    void insert(AiClientConfig aiClientConfig);

    /**
     * 根据主键 ID 更新 AI 客户端关联配置记录。
     *
     * @param aiClientConfig AI 客户端关联配置对象
     */
    void updateById(AiClientConfig aiClientConfig);

    /**
     * 根据来源 ID 更新 AI 客户端关联配置记录。
     *
     * @param aiClientConfig AI 客户端关联配置对象
     */
    void updateBySourceId(AiClientConfig aiClientConfig);

    /**
     * 根据主键 ID 删除 AI 客户端关联配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据来源 ID 删除 AI 客户端关联配置记录。
     *
     * @param sourceId 来源 ID
     */
    void deleteBySourceId(String sourceId);

    /**
     * 根据主键 ID 查询 AI 客户端关联配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端关联配置对象
     */
    AiClientConfig queryById(Long id);

    /**
     * 根据来源 ID 查询 AI 客户端关联配置记录。
     *
     * @param sourceId 来源 ID
     * @return AI 客户端关联配置对象
     */
    AiClientConfig queryBySourceId(String sourceId);

    /**
     * 根据目标 ID 查询 AI 客户端关联配置记录。
     *
     * @param targetId 目标 ID
     * @return AI 客户端关联配置对象
     */
    AiClientConfig queryByTargetId(String targetId);

    /**
     * 根据来源类型和来源 ID 查询 AI 客户端关联配置记录。
     *
     * @param sourceType 来源类型
     * @param sourceId 来源 ID
     * @return AI 客户端关联配置对象
     */
    List<AiClientConfig> queryBySourceTypeAndId(@Param("sourceType") String sourceType, @Param("sourceId") String sourceId);

    /**
     * 根据目标类型和目标 ID 查询 AI 客户端关联配置记录。
     *
     * @param targetType 目标类型
     * @param targetId 目标 ID
     * @return AI 客户端关联配置对象
     */
    AiClientConfig queryByTargetTypeAndId(@Param("targetType") String targetType, @Param("targetId") String targetId);

    /**
     * 查询全部启用状态的 AI 客户端关联配置记录。
     *
     * @return AI 客户端关联配置列表
     */
    List<AiClientConfig> queryEnabledConfigs();

    /**
     * 查询全部 AI 客户端关联配置记录。
     *
     * @return AI 客户端关联配置列表
     */
    List<AiClientConfig> queryAll();

}
