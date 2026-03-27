package com.beaker.domain.agent.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.beaker.domain.agent.model.valobj.enums.AiClientAdvisorTypeEnumVO;
import com.beaker.domain.agent.model.valobj.AiClientAdvisorVO;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author beaker
 * @Date 2026/3/23 20:39
 * @Description 顾问角色节点
 */
@Service
@Slf4j
public class AiClientAdvisorNode extends AbstractArmorySupport{

    @Resource
    private VectorStore vectorStore;

    @Resource
    private AiClientNode aiClientNode;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建, Advisor 节点 {}", JSON.toJSONString(requestParameter));

        List<AiClientAdvisorVO> aiClientAdvisorList = dynamicContext.getValue(dataName());

        if (aiClientAdvisorList == null || aiClientAdvisorList.isEmpty()) {
            log.warn("没有需要被初始化的 ai client advisor");
            return router(requestParameter, dynamicContext);
        }

        for (AiClientAdvisorVO aiClientAdvisorVO : aiClientAdvisorList) {
            // 创建 advisor
            Advisor advisor = createAdvisor(aiClientAdvisorVO);
            // 注册 advisor bean
            registerBean(beanName(aiClientAdvisorVO.getAdvisorId()), Advisor.class, advisor);
        }
        
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientNode;
    }

    @Override
    protected String beanName(String id) {
        return AiAgentEnumVO.AI_CLIENT_ADVISOR.getBeanName(id);
    }

    @Override
    protected String dataName() {
        return AiAgentEnumVO.AI_CLIENT_ADVISOR.getDataName();
    }

    private Advisor createAdvisor(AiClientAdvisorVO aiClientAdvisorVO) {
        String advisorType = aiClientAdvisorVO.getAdvisorType();
        AiClientAdvisorTypeEnumVO advisorTypeEnum = AiClientAdvisorTypeEnumVO.getByCode(advisorType);
        return advisorTypeEnum.createAdvisor(aiClientAdvisorVO, vectorStore);
    }
}
