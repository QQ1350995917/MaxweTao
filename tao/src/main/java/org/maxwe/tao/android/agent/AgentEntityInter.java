package org.maxwe.tao.android.agent;

/**
 * Created by Pengwei Ding on 2016-12-31 15:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentEntityInter extends AgentEntity {
    private String t;
    private String ordPassword;
    private String newPassword;
    private String cellPhoneCode;

    public AgentEntityInter() {
        super();
    }

    public AgentEntityInter(String cellPhoneCode,String token,int type){

    }

    public AgentEntityInter(AgentEntity agentEntity) {
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

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getOrdPassword() {
        return ordPassword;
    }

    public void setOrdPassword(String ordPassword) {
        this.ordPassword = ordPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCellPhoneCode() {
        return cellPhoneCode;
    }

    public void setCellPhoneCode(String cellPhoneCode) {
        this.cellPhoneCode = cellPhoneCode;
    }

    @Override
    public String toString() {
        return "cellphone = " + getCellphone();
    }
}
