package org.maxwe.tao.android.trade;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-11 21:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TradeModel extends SessionModel {
    private String targetMark;
    private int type;
    private String actCode;
    private int codeNum;

    public TradeModel() {
        super();
    }

    public TradeModel(SessionModel sessionModel,int type,int codeNum) {
        super(sessionModel.getT(),sessionModel.getMark(),sessionModel.getCellphone(),sessionModel.getApt());
        this.type = type;
        this.codeNum = codeNum;
    }


    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
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

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    @Override
    public String toString() {
        return super.toString() + "TradeModel{" +
                "targetMark=" + targetMark +
                ", type=" + type +
                ", codeNum=" + codeNum +
                '}';
    }
}
