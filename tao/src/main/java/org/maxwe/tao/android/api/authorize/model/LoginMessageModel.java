package org.maxwe.tao.android.api.authorize.model;

import org.maxwe.tao.android.api.authorize.LoginMessageEntity;

/**
 * Created by Pengwei Ding on 2017-02-08 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */


public class LoginMessageModel {

    private LoginMessageEntity data;

    public LoginMessageModel() {
        super();
    }

    public LoginMessageEntity getData() {
        return data;
    }

    public void setData(LoginMessageEntity data) {
        this.data = data;
    }
}
