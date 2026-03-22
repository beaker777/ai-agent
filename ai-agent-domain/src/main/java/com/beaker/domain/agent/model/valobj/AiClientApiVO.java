package com.beaker.domain.agent.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author beaker
 * @Date 2026/3/22 14:35
 * @Description Openai api 配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiClientApiVO {

    /** 全局唯一配置ID。 */
    private String apiId;
    /** API基础URL。 */
    private String baseUrl;
    /** API密钥。 */
    private String apiKey;
    /** 补全API路径。 */
    private String completionsPath;
    /** 嵌入API路径。 */
    private String embeddingsPath;
}
