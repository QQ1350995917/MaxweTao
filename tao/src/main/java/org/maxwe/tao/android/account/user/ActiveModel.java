package org.maxwe.tao.android.account.user;

import android.text.TextUtils;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-10 17:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ActiveModel extends SessionModel {
    private String actCode;

    public ActiveModel() {
        super();
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    @Override
    public boolean isParamsOk() {
        if (!TextUtils.isEmpty(this.getActCode())) {
            return true && super.isParamsOk();
        }
        return false;
    }
}
