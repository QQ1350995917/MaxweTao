package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-12 16:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TrunkModel extends SessionModel {
    private String targetMark;
    private AgentEntity agentEntity;

    public TrunkModel() {
        super();
    }

    public TrunkModel(SessionModel sessionModel, String targetMark) {
        super(sessionModel.getT(), sessionModel.getMark(), sessionModel.getCellphone(), sessionModel.getApt());
        this.targetMark = targetMark;
    }

    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
    }

    public AgentEntity getAgentEntity() {
        return agentEntity;
    }

    public void setAgentEntity(AgentEntity agentEntity) {
        this.agentEntity = agentEntity;
    }

    @Override
    public String toString() {
        return super.toString() + "TrunkModel{" +
                "targetMark='" + targetMark + '\'' +
                ", agentEntity=" + agentEntity +
                '}';
    }
}
