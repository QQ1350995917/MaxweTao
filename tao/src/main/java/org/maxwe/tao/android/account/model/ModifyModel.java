package org.maxwe.tao.android.account.model;

import android.text.TextUtils;

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

    public ModifyModel(String t, String mark, String cellphone) {
        super(t, mark, cellphone);
    }

    public ModifyModel(SessionModel sessionModel, String oldPassword, String newPassword) {
        super(sessionModel.getT(), sessionModel.getMark(), sessionModel.getCellphone());
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ModifyModel(String t, String mark, String cellphone, String oldPassword, String newPassword) {
        super(t, mark, cellphone);
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
