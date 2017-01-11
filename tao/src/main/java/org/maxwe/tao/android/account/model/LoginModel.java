package org.maxwe.tao.android.account.model;

import android.text.TextUtils;

import com.alibaba.fastjson.serializer.PropertyFilter;

import org.maxwe.tao.android.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class LoginModel implements Serializable {

    private String cellphone;
    private String password;

    public LoginModel() {
        super();
    }

    public LoginModel(String cellphone, String password) {
        this.cellphone = cellphone;
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isParamsOK() {
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
