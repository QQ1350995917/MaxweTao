package org.maxwe.tao.android.account.model;

import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.utils.PasswordUtils;
import org.maxwe.tao.android.utils.StringUtils;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 注册请求模型
 */
public class AccountSignUpRequestModel extends TokenModel {
    private String smsCode;
    private String password;

    public AccountSignUpRequestModel() {
        super();
    }

    public AccountSignUpRequestModel(String cellphone, String smsCode, String password,int apt) {
        super(null, 0, cellphone,apt);
        this.smsCode = smsCode;
        this.password = password;
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

    public boolean isAccountSignUpParamsOk() {
        if (!CellPhoneUtils.isCellphone(this.getCellphone())) {
            return false;
        }
        if (StringUtils.isEmpty(this.getSmsCode())) {
            return false;
        }
        if (!PasswordUtils.isPlainPasswordOk(this.getPassword())) {
            return false;
        }
        return true;
    }
}
