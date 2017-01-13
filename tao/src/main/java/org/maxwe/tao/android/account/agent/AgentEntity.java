package org.maxwe.tao.android.account.agent;

import org.maxwe.tao.android.account.AccountEntity;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentEntity extends AccountEntity {
    private int reach; // 就代理关系达成一致意见 数据库可为空，1达成，其他不达成
    private String pMark; // 上级的显示ID
    private String levelId; // 级别的ID
    private int haveCodes; // 累计购买
    private int spendCodes; // 已经消费
    private int leftCodes; // 当前剩余
    private String trueName; // 真实姓名
    private String zhifubao;// 支付宝账户
    private long pIdTime;
    private long reachTime;

    public AgentEntity() {
        super();
    }

    /**
     * 测试后构建使用
     */
    public AgentEntity(String mark,String levelId){
        this.setMark(mark);
        this.setLevelId(levelId);
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public String getpMark() {
        return pMark;
    }

    public void setpMark(String pMark) {
        this.pMark = pMark;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public int getHaveCodes() {
        return haveCodes;
    }

    public void setHaveCodes(int haveCodes) {
        this.haveCodes = haveCodes;
    }

    public int getSpendCodes() {
        return spendCodes;
    }

    public void setSpendCodes(int spendCodes) {
        this.spendCodes = spendCodes;
    }

    public int getLeftCodes() {
        return leftCodes;
    }

    public void setLeftCodes(int leftCodes) {
        this.leftCodes = leftCodes;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    public long getpIdTime() {
        return pIdTime;
    }

    public void setpIdTime(long pIdTime) {
        this.pIdTime = pIdTime;
    }

    public long getReachTime() {
        return reachTime;
    }

    public void setReachTime(long reachTime) {
        this.reachTime = reachTime;
    }


    public String getCodeStatusString(){
        return this.getSpendCodes() + "/"  + this.getLeftCodes() + "/" + this.getHaveCodes();
    }

    @Override
    public String toString() {
        return super.toString() + "AgentEntity{" +
                "reach='" + reach + '\'' +
                ", pMark='" + pMark + '\'' +
                ", levelId='" + levelId + '\'' +
                ", haveCodes=" + haveCodes +
                ", spendCodes=" + spendCodes +
                ", leftCodes=" + leftCodes +
                ", trueName='" + trueName + '\'' +
                ", zhifubao='" + zhifubao + '\'' +
                ", pIdTime='" + pIdTime + '\'' +
                ", reachTime='" + reachTime + '\'' +
                '}';
    }
}
