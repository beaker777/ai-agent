package com.beaker.domain.agent.service.execute.auto.step;

import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.beaker.domain.agent.adapter.repository.IAgentRepository;
import com.beaker.domain.agent.model.entity.ExecuteCommandEntity;
import com.beaker.domain.agent.model.valobj.enums.AiAgentEnumVO;
import com.beaker.domain.agent.service.armory.AbstractArmorySupport;
import com.beaker.domain.agent.service.execute.auto.step.factory.DefaultAutoAgentExecuteStrategyFactory;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @Author beaker
 * @Date 2026/3/26 18:18
 * @Description execute 流程模板类
 */
public abstract class AbstractExecuteSupport extends AbstractMultiThreadStrategyRouter<ExecuteCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext, String> {

    private final Logger log = LoggerFactory.getLogger(AbstractArmorySupport.class);

    @Resource
    protected ApplicationContext applicationContext;

    @Resource
    protected IAgentRepository agentRepository;

    public static final String CHAT_MEMORY_CONVERSATION_ID_KEY = "chat_memory_conversation_id";
    public static final String CHAT_MEMORY_RETRIEVE_SIZE_KEY = "chat_memory_response_size";

    @Override
    protected void multiThread(ExecuteCommandEntity executeCommandEntity, DefaultAutoAgentExecuteStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的
    }

    protected ChatClient getChatClientByClientId(String clientId) {
        return getBean(AiAgentEnumVO.AI_CLIENT.getBeanName(clientId));
    }

    protected <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }
}
