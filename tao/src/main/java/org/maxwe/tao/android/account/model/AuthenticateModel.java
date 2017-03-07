package org.maxwe.tao.android.account.model;


import org.maxwe.tao.android.utils.PasswordUtils;

/**
 * Created by Pengwei Ding on 2017-03-03 15:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 敏感操作模型
 * 该模型里需要对操作进行密码验证
 */
public class AuthenticateModel extends TokenModel {
    private String authenticatePassword;//敏感操作的验证密码

    public AuthenticateModel() {
        super();
    }

    public AuthenticateModel(String authenticatePassword) {
        super();
        this.authenticatePassword = authenticatePassword;
    }

    public AuthenticateModel(TokenModel tokenModel) {
        super(tokenModel);
    }

    public AuthenticateModel(TokenModel tokenModel,String authenticatePassword) {
        super(tokenModel);
        this.authenticatePassword = authenticatePassword;
    }

    public String getAuthenticatePassword() {
        return authenticatePassword;
    }

    public void setAuthenticatePassword(String authenticatePassword) {
        this.authenticatePassword = authenticatePassword;
    }

    public boolean isAuthenticateParamsOk(){
        return super.isTokenParamsOk() && PasswordUtils.isPlainPasswordOk(this.getAuthenticatePassword());
    }
}
