package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientConfig;
import org.apache.ibatis.annotations.Mapper;

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
     * 更新 AI 客户端关联配置记录。
     *
     * @param aiClientConfig AI 客户端关联配置对象
     */
    void update(AiClientConfig aiClientConfig);

    /**
     * 根据主键ID查询 AI 客户端关联配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端关联配置对象
     */
    AiClientConfig queryById(Long id);

    /**
     * 查询全部 AI 客户端关联配置记录。
     *
     * @return AI 客户端关联配置列表
     */
    List<AiClientConfig> queryAll();

}
