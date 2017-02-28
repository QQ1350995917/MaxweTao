package org.maxwe.tao.android.account.agent;

import android.text.TextUtils;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-10 17:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class BankModel extends TokenModel {
    private String trueName;
    private String zhifubao;
    private long timestamp;// 响应字段

    public BankModel() {
        super();
    }

    public BankModel(TokenModel sessionModel, String trueName, String zhifubao) {
        super(sessionModel.getT(),sessionModel.getId(),sessionModel.getCellphone(),sessionModel.getApt());
        this.trueName = trueName;
        this.zhifubao = zhifubao;
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
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public boolean isParamsOk() {
        if (TextUtils.isEmpty(this.getTrueName())
                || TextUtils.isEmpty(this.getZhifubao())
                || TextUtils.isEmpty(this.getVerification())
                || this.getVerification().length() < 6
                || this.getVerification().length() > 12) {
            return false;
        }
        return super.isTokenParamsOk();
    }
}
