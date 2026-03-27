package com.beaker.domain.agent.model.valobj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author beaker
 * @Date 2026/3/26 16:48
 * @Description client 类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AiClientTypeEnumVO {

    DEFAULT("DEFAULT", "通用的"),
    TASK_ANALYZER_CLIENT("TASK_ANALYZER_CLIENT", "任务分析和状态判断"),
    PRECISION_EXECUTOR_CLIENT("PRECISION_EXECUTOR_CLIENT", "具体任务执行"),
    QUALITY_SUPERVISOR_CLIENT("QUALITY_SUPERVISOR_CLIENT", "质量检查和优化"),
    ;

    private String code;
    private String info;

}
