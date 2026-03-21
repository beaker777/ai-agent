package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端配置 DAO 接口。
 */
@Mapper
public interface IAiClientDao {

    /**
     * 新增 AI 客户端配置记录。
     *
     * @param aiClient AI 客户端配置对象
     */
    void insert(AiClient aiClient);

    /**
     * 更新 AI 客户端配置记录。
     *
     * @param aiClient AI 客户端配置对象
     */
    void update(AiClient aiClient);

    /**
     * 根据主键ID查询 AI 客户端配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端配置对象
     */
    AiClient queryById(Long id);

    /**
     * 查询全部 AI 客户端配置记录。
     *
     * @return AI 客户端配置列表
     */
    List<AiClient> queryAll();

}
