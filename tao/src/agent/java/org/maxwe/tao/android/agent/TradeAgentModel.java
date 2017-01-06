package org.maxwe.tao.android.agent;

/**
 * Created by Pengwei Ding on 2017-01-06 11:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TradeAgentModel extends AgentEntityInter{
    private int tradeCode;//交易授权码的数量
    private AgentEntity authorizedAgent;

    public TradeAgentModel() {
        super();
    }

    public TradeAgentModel(AgentEntityInter agentEntity) {
        this.setT(agentEntity.getT());
        this.setAgentId(agentEntity.getAgentId());
        this.setAgentPId(agentEntity.getAgentPId());
        this.setCellphone(agentEntity.getCellphone());
        this.setPassword(agentEntity.getPassword());
        this.setName(agentEntity.getName());
        this.setNamed(agentEntity.getNamed());
        this.setGrantCode(agentEntity.getGrantCode());
        this.setType(agentEntity.getType());
        this.setLevel(agentEntity.getLevel());
        this.setStatus(agentEntity.getStatus());
        this.setHaveCodes(agentEntity.getHaveCodes());
        this.setSpendCodes(agentEntity.getSpendCodes());
        this.setLeftCodes(agentEntity.getLeftCodes());
        this.setCreateTime(agentEntity.getCreateTime());
        this.setUpdateTime(agentEntity.getUpdateTime());
    }

    public int getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(int tradeCode) {
        this.tradeCode = tradeCode;
    }

    public AgentEntity getAuthorizedAgent() {
        return authorizedAgent;
    }

    public void setAuthorizedAgent(AgentEntity authorizedAgent) {
        this.authorizedAgent = authorizedAgent;
    }
}
