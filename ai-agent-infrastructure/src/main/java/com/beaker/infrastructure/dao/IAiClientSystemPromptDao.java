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
     * 根据主键 ID 更新 AI 客户端系统提示词记录。
     *
     * @param aiClientSystemPrompt AI 客户端系统提示词对象
     */
    void updateById(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 根据提示词 ID 更新 AI 客户端系统提示词记录。
     *
     * @param aiClientSystemPrompt AI 客户端系统提示词对象
     */
    void updateByPromptId(AiClientSystemPrompt aiClientSystemPrompt);

    /**
     * 根据主键 ID 删除 AI 客户端系统提示词记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据提示词 ID 删除 AI 客户端系统提示词记录。
     *
     * @param promptId 提示词 ID
     */
    void deleteByPromptId(String promptId);

    /**
     * 根据主键 ID 查询 AI 客户端系统提示词记录。
     *
     * @param id 主键 ID
     * @return AI 客户端系统提示词对象
     */
    AiClientSystemPrompt queryById(Long id);

    /**
     * 根据提示词 ID 查询 AI 客户端系统提示词记录。
     *
     * @param promptId 提示词 ID
     * @return AI 客户端系统提示词对象
     */
    AiClientSystemPrompt queryByPromptId(String promptId);

    /**
     * 查询全部启用状态的 AI 客户端系统提示词记录。
     *
     * @return AI 客户端系统提示词列表
     */
    List<AiClientSystemPrompt> queryEnabledPrompts();

    /**
     * 根据提示词名称查询 AI 客户端系统提示词记录。
     *
     * @param promptName 提示词名称
     * @return AI 客户端系统提示词列表
     */
    List<AiClientSystemPrompt> queryByPromptName(String promptName);

    /**
     * 查询全部 AI 客户端系统提示词记录。
     *
     * @return AI 客户端系统提示词列表
     */
    List<AiClientSystemPrompt> queryAll();

}
