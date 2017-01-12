package org.maxwe.tao.android.account.model;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import org.maxwe.tao.android.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class RegisterModel  extends SessionModel {

    private String cellphone;
    private String smsCode;
    private String password;

    public RegisterModel() {
        super();
    }

    public RegisterModel(String cellphone, String smsCode, String password) {
        this.cellphone = cellphone;
        this.smsCode = smsCode;
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSONField(serialize=false)
    public boolean isParamsOK(){
        if (CellPhoneUtils.isCellphone(this.getCellphone())
                && !TextUtils.isEmpty(this.getPassword())
                && this.getPassword().length() >= 6
                && this.getPassword().length() <= 12) {
            return true;
        } else {
            return false;
        }
    }
}
