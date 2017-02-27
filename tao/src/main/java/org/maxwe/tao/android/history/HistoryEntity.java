package org.maxwe.tao.android.history;

import org.maxwe.tao.android.utils.DateTimeUtils;

/**
 * Created by Pengwei Ding on 2017-01-12 12:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class HistoryEntity {
    private int fromId; // 操作来源ID
    private int toId; // 操作流向ID，如果类型为1，则此ID为后来补充
    private int type; // 1激活码，2批量激活码
    private String actCode; //如果类型为1，则是向单个用激活
    private int codeNum;//如果类型为2，则表示交易为数量
    private long createTime;//创建时间
    private long updateTime;//更新时间

    public HistoryEntity() {
        super();
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getSwapTimeString(){
        String fullTime = DateTimeUtils.parseLongToFullTime(this.getCreateTime());
        String replace = fullTime.replace(" ", "\n");
        return replace;
    }

    @Override
    public String toString() {
        return super.toString() + "HistoryModel{" +
                "updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", codeNum=" + codeNum +
                ", actCode='" + actCode + '\'' +
                ", type=" + type +
                ", toId='" + toId + '\'' +
                ", fromId='" + fromId + '\'' +
                '}';
    }
}
