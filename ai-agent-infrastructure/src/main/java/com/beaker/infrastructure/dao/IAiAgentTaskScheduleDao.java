package com.beaker.infrastructure.dao;

import com.beaker.infrastructure.dao.po.AiAgentTaskSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 智能体任务调度 DAO 接口。
 */
@Mapper
public interface IAiAgentTaskScheduleDao {

    /**
     * 新增 AI 智能体任务调度记录。
     *
     * @param aiAgentTaskSchedule AI 智能体任务调度对象
     */
    void insert(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 更新 AI 智能体任务调度记录。
     *
     * @param aiAgentTaskSchedule AI 智能体任务调度对象
     */
    void update(AiAgentTaskSchedule aiAgentTaskSchedule);

    /**
     * 根据主键ID查询 AI 智能体任务调度记录。
     *
     * @param id 主键ID
     * @return AI 智能体任务调度对象
     */
    AiAgentTaskSchedule queryById(Long id);

    /**
     * 查询全部 AI 智能体任务调度记录。
     *
     * @return AI 智能体任务调度列表
     */
    List<AiAgentTaskSchedule> queryAll();

}
