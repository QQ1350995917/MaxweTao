package org.maxwe.tao.android.account.agent;

import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-01-10 17:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentModel extends TokenModel {
    private AgentEntity agentEntity; // 响应字段
    private LevelEntity levelEntity; // 响应字段

    public AgentModel() {
        super();
    }

    public AgentEntity getAgentEntity() {
        return agentEntity;
    }

    public void setAgentEntity(AgentEntity agentEntity) {
        this.agentEntity = agentEntity;
    }

    public LevelEntity getLevelEntity() {
        return levelEntity;
    }

    public void setLevelEntity(LevelEntity levelEntity) {
        this.levelEntity = levelEntity;
    }
}
