package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-12 16:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TrunkModel extends SessionModel {
    private String mark;
    private AgentEntity agentEntity;

    public TrunkModel() {
        super();
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
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
                "mark='" + mark + '\'' +
                ", agentEntity=" + agentEntity +
                '}';
    }
}
