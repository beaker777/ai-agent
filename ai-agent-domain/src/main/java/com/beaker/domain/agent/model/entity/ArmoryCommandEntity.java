package com.beaker.domain.agent.model.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author beaker
 * @Date 2026/3/22 14:05
 * @Description 装配命令
 */
@Data
public class ArmoryCommandEntity {

    /**
     * 命令类型
     */
    private String commandType;
    /**
     * 命令索引
     */
    private List<String> commandIdList;
}
