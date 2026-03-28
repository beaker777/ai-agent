package com.beaker.config;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import com.beaker.types.common.Constants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @Author beaker
 * @Date 2026/3/27 18:37
 * @Description AiAgent 自动配置类
 */
@Configuration
@EnableConfigurationProperties(AiAgentAutoConfigProperties.class)
@ConditionalOnProperty(prefix = "spring.ai.agent.auto-config", name = "enable", havingValue = "true")
@Slf4j
public class AiAgentAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private AiAgentAutoConfigProperties aiAgentAutoConfigProperties;

    @Resource
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            log.info("AI Agent 自动装配开始, 配置: {}", aiAgentAutoConfigProperties);

            // 检查配置是否有效
            if (!aiAgentAutoConfigProperties.isEnable()) {
                log.info("AI Agent 自动装配未启用");
                return;
            }

            // 获取 client ids
            List<String> clientIds = aiAgentAutoConfigProperties.getClientIds();
            if (clientIds == null || clientIds.isEmpty()) {
                log.warn("AI Agent 自动装配的 client id 列表为空");
                return;
            }

            // 解析 client ids
            List<String> commandIds;
            if (clientIds.size() == 1 && clientIds.get(0).contains(Constants.SPLIT)) {
                // 处理逗号分割的字符串
                commandIds  = Arrays.stream(clientIds.get(0).split(Constants.SPLIT))
                        .map(String::trim)
                        .filter(id -> !id.isBlank())
                        .toList();
            } else {
                commandIds = clientIds;
            }

            log.info("解析 client id 列表完成, clientIdList: {}", commandIds);

            // 执行自动装配
            StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler =
                    defaultArmoryStrategyFactory.armoryStrategyHandler();
            String apply = armoryStrategyHandler.apply(
                    ArmoryCommandEntity.builder()
                            .commandType(AiAgentEnumVO.AI_CLIENT.getCode())
                            .commandIdList(commandIds)
                            .build(),
                    new DefaultArmoryStrategyFactory.DynamicContext());

            log.info("自动装配完成, 结果: {}", apply);
        } catch (Exception e) {
            log.error("Ai Agent 自动装配失败", e);
        }
    }
}
