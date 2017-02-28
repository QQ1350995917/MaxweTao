package org.maxwe.tao.android.account.user;

import android.text.TextUtils;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-10 17:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ActiveModel extends TokenModel {
    private String actCode;

    public ActiveModel() {
        super();
    }

    public ActiveModel(TokenModel sessionModel, String actCode) {
        super(sessionModel.getT(),sessionModel.getId(),sessionModel.getCellphone(),sessionModel.getApt());
        this.actCode = actCode;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public boolean isParamsOk() {
        if (!TextUtils.isEmpty(this.getActCode())) {
            return true && super.isTokenParamsOk();
        }
        return false;
    }
}
