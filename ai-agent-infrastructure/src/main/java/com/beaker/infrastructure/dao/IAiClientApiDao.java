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
     * 更新 AI 客户端 API 配置记录。
     *
     * @param aiClientApi AI 客户端 API 配置对象
     */
    void update(AiClientApi aiClientApi);

    /**
     * 根据主键ID查询 AI 客户端 API 配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端 API 配置对象
     */
    AiClientApi queryById(Long id);

    /**
     * 查询全部 AI 客户端 API 配置记录。
     *
     * @return AI 客户端 API 配置列表
     */
    List<AiClientApi> queryAll();

}
