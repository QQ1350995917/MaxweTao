package org.maxwe.tao.android.account.agent;

import org.maxwe.tao.android.account.AccountEntity;

/**
 * Created by Pengwei Ding on 2017-01-10 17:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentEntity extends AccountEntity {
    private int pId;
    private int reach; // 就代理关系达成一致意见 数据库可为空，1达成，其他不达成
    private int haveCodes; // 累计购买
    private int spendCodes; // 已经消费
    private int leftCodes; // 当前剩余
    private String trueName; // 真实姓名
    private String zhifubao;// 支付宝账户
    private long pIdTime;
    private long reachTime;
    private long bankTime;

    public AgentEntity() {
        super();
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
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

    public long getBankTime() {
        return bankTime;
    }

    public void setBankTime(long bankTime) {
        this.bankTime = bankTime;
    }

    public String getCodeStatusString(){
        return this.getSpendCodes() + "/"  + this.getLeftCodes() + "/" + this.getHaveCodes();
    }

    @Override
    public String toString() {
        return super.toString() + "AgentEntity{" +
                "reach='" + reach + '\'' +
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
