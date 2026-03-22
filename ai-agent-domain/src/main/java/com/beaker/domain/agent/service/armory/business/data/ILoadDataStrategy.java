package com.beaker.domain.agent.service.armory.business.data;

import com.beaker.domain.agent.model.entity.ArmoryCommandEntity;
import com.beaker.domain.agent.service.armory.factory.DefaultArmoryStrategyFactory;

/**
 * @Author beaker
 * @Date 2026/3/22 14:01
 * @Description 数据加载策略
 */
public interface ILoadDataStrategy {

    void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext);
}
