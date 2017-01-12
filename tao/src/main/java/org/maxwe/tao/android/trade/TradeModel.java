package org.maxwe.tao.android.trade;

import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-11 21:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TradeModel extends SessionModel {
    private AgentEntity toAgentEntity;
    private int type;
    private String actCode;
    private String levelId;
    private int codeNum;

    public TradeModel() {
        super();
    }

    public TradeModel(SessionModel sessionModel,int type,int codeNum) {
        super(sessionModel.getT(),sessionModel.getMark(),sessionModel.getCellphone());
        this.type = type;
        this.codeNum = codeNum;
    }


    public AgentEntity getToAgentEntity() {
        return toAgentEntity;
    }

    public void setToAgentEntity(AgentEntity toAgentEntity) {
        this.toAgentEntity = toAgentEntity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    @Override
    public String toString() {
        return super.toString() + "TradeModel{" +
                "toAgentEntity=" + toAgentEntity +
                ", type=" + type +
                ", levelId=" + levelId +
                ", codeNum=" + codeNum +
                '}';
    }
}
