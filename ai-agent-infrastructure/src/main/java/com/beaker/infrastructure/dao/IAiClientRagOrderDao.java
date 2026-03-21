package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientRagOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端知识库配置 DAO 接口。
 */
@Mapper
public interface IAiClientRagOrderDao {

    /**
     * 新增 AI 客户端知识库配置记录。
     *
     * @param aiClientRagOrder AI 客户端知识库配置对象
     */
    void insert(AiClientRagOrder aiClientRagOrder);

    /**
     * 更新 AI 客户端知识库配置记录。
     *
     * @param aiClientRagOrder AI 客户端知识库配置对象
     */
    void update(AiClientRagOrder aiClientRagOrder);

    /**
     * 根据主键ID查询 AI 客户端知识库配置记录。
     *
     * @param id 主键ID
     * @return AI 客户端知识库配置对象
     */
    AiClientRagOrder queryById(Long id);

    /**
     * 查询全部 AI 客户端知识库配置记录。
     *
     * @return AI 客户端知识库配置列表
     */
    List<AiClientRagOrder> queryAll();

}
