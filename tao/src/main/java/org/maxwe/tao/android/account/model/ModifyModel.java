package org.maxwe.tao.android.account.model;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Pengwei Ding on 2017-01-10 17:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ModifyModel extends SessionModel {

    private String oldPassword;
    private String newPassword;

    public ModifyModel() {
        super();
    }

    public ModifyModel(SessionModel sessionModel, String oldPassword, String newPassword) {
        super(sessionModel.getT(), sessionModel.getId(), sessionModel.getCellphone(),sessionModel.getApt());
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    @JSONField(serialize=false)
    public boolean isParamsOk() {
        boolean paramsOk = super.isParamsOk();
        if (!TextUtils.isEmpty(this.getOldPassword())
                && !TextUtils.isEmpty(this.getNewPassword())
                && this.getNewPassword().length() >= 6
                && this.getNewPassword().length() <= 12) {
            return true && paramsOk;
        } else {
            return false;
        }
    }
}
