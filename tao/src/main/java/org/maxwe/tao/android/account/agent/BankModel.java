package org.maxwe.tao.android.account.agent;

import android.text.TextUtils;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-10 17:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class BankModel extends SessionModel {
    private String trueName;
    private String zhifubao;
    private String password;
    private long timestamp;// 响应字段

    public BankModel() {
        super();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BankModel{" +
                "trueName='" + trueName + '\'' +
                ", zhifubao='" + zhifubao + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        if (TextUtils.isEmpty(this.getTrueName())
                || TextUtils.isEmpty(this.getZhifubao())
                || TextUtils.isEmpty(this.getPassword())
                || this.getPassword().length() < 6
                || this.getPassword().length() > 12) {
            return false;
        }
        return super.isParamsOk();
    }
}
