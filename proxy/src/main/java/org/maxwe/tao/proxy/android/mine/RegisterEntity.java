package org.maxwe.tao.proxy.android.mine;

/**
 * Created by Pengwei Ding on 2016-12-30 14:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class RegisterEntity {
    private String t;
    private String cellphone;
    private String cellphoneCode;
    private String password;

    public RegisterEntity() {
        super();
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphoneCode() {
        return cellphoneCode;
    }

    public void setCellphoneCode(String cellphoneCode) {
        this.cellphoneCode = cellphoneCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
