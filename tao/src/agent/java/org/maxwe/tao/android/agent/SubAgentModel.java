package org.maxwe.tao.android.agent;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-05 21:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SubAgentModel extends AgentEntityInter {
    private int pageIndex;
    private int counter;
    private LinkedList<AgentEntity> subAgents;

    public SubAgentModel() {
        super();
    }

    public SubAgentModel(AgentEntityInter agentEntity) {
        this.setT(agentEntity.getT());
        this.setAgentId(agentEntity.getAgentId());
        this.setAgentPId(agentEntity.getAgentPId());
        this.setCellphone(agentEntity.getCellphone());
        this.setPassword(agentEntity.getPassword());
        this.setName(agentEntity.getName());
        this.setNamed(agentEntity.getNamed());
        this.setCode(agentEntity.getCode());
        this.setType(agentEntity.getType());
        this.setLevel(agentEntity.getLevel());
        this.setStatus(agentEntity.getStatus());
        this.setHaveCodes(agentEntity.getHaveCodes());
        this.setSpendCodes(agentEntity.getSpendCodes());
        this.setLeftCodes(agentEntity.getLeftCodes());
        this.setCreateTime(agentEntity.getCreateTime());
        this.setUpdateTime(agentEntity.getUpdateTime());
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public LinkedList<AgentEntity> getSubAgents() {
        return subAgents;
    }

    public void setSubAgents(LinkedList<AgentEntity> subAgents) {
        this.subAgents = subAgents;
    }
}
