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
     * 根据主键 ID 更新 AI 客户端配置记录。
     *
     * @param aiClient AI 客户端配置对象
     */
    void updateById(AiClient aiClient);

    /**
     * 根据客户端 ID 更新 AI 客户端配置记录。
     *
     * @param aiClient AI 客户端配置对象
     */
    void updateByClientId(AiClient aiClient);

    /**
     * 根据主键 ID 删除 AI 客户端配置记录。
     *
     * @param id 主键 ID
     */
    void deleteById(Long id);

    /**
     * 根据客户端 ID 删除 AI 客户端配置记录。
     *
     * @param clientId 客户端 ID
     */
    void deleteByClientId(String clientId);

    /**
     * 根据主键 ID 查询 AI 客户端配置记录。
     *
     * @param id 主键 ID
     * @return AI 客户端配置对象
     */
    AiClient queryById(Long id);

    /**
     * 根据客户端 ID 查询 AI 客户端配置记录。
     *
     * @param clientId 客户端 ID
     * @return AI 客户端配置对象
     */
    AiClient queryByClientId(String clientId);

    /**
     * 查询全部启用状态的 AI 客户端配置记录。
     *
     * @return AI 客户端配置列表
     */
    List<AiClient> queryEnabledClients();

    /**
     * 根据客户端名称查询 AI 客户端配置记录。
     *
     * @param clientName 客户端名称
     * @return AI 客户端配置列表
     */
    List<AiClient> queryByClientName(String clientName);

    /**
     * 查询全部 AI 客户端配置记录。
     *
     * @return AI 客户端配置列表
     */
    List<AiClient> queryAll();

}
