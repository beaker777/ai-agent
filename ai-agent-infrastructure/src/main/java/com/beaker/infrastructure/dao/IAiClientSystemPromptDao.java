package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiClientSystemPrompt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 客户端系统提示词 DAO 接口。
 */
@Mapper
public interface IAiClientSystemPromptDao {

    /**
     * 新增 AI 客户端系统提示词记录。
     *
     * @param aiClientSystemPrompt AI 客户端系统提示词对象
     */
    void insert(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 更新 AI 客户端系统提示词记录。
     *
     * @param aiClientSystemPrompt AI 客户端系统提示词对象
     */
    void update(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 根据主键ID查询 AI 客户端系统提示词记录。
     *
     * @param id 主键ID
     * @return AI 客户端系统提示词对象
     */
    AiClientSystemPrompt queryById(Long id);

    /**
     * 查询全部 AI 客户端系统提示词记录。
     *
     * @return AI 客户端系统提示词列表
     */
    List<AiClientSystemPrompt> queryAll();

}
