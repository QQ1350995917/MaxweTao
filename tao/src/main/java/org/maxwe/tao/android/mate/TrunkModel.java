package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-01-12 16:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TrunkModel extends SessionModel {
    private int targetId;
    private AgentEntity agentEntity; // 相应字段
    private LevelEntity levelEntity; // 相应字段

    public TrunkModel() {
        super();
    }

    public TrunkModel(SessionModel sessionModel, int targetId) {
        super(sessionModel.getT(), sessionModel.getId(), sessionModel.getCellphone(), sessionModel.getApt());
        this.targetId = targetId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
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

    @Override
    public String toString() {
        return super.toString() + "TrunkModel{" +
                ", agentEntity=" + agentEntity +
                '}';
    }
}
