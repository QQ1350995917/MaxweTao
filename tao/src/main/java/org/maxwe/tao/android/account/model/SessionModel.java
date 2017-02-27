package org.maxwe.tao.android.account.model;

import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.annotation.JSONField;

import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.utils.CryptionUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SessionModel implements Serializable {
    private String t;
    private int id;
    private String cellphone;
    private int apt; //app 类型
    private String verification;//敏感操作的验证密码
    private String sign;

    public SessionModel() {
        super();
    }

    public SessionModel(String t, int id, String cellphone, int apt) {
        this.t = t;
        this.id = id;
        this.cellphone = cellphone;
        this.apt = apt;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getApt() {
        return apt;
    }

    public void setApt(int apt) {
        this.apt = apt;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @JSONField(serialize = false)
    public String getEncryptSing() throws Exception {
        if (this.getId() == 0 || !CellPhoneUtils.isCellphone(this.getCellphone())) {
            return null;
        }
        String password = (this.getCellphone() + new StringBuffer(this.getCellphone()).reverse()).substring(1, 17);//生成的ID是11位，补全16位密码
        String content = this.getId() + "-" + System.currentTimeMillis() + "-" + this.getCellphone();
        String encodeContent = new String(Base64.encodeToString(content.getBytes(), Base64.NO_WRAP));
        byte[] encryptResult = CryptionUtils.encryptCustomer(encodeContent, password);
        String encryptResultStr = CryptionUtils.parseByte2HexStr(encryptResult);
        return encryptResultStr;
    }

    @JSONField(serialize = false)
    public boolean isDecryptSignOK() throws Exception {
        String password = (this.getCellphone() + new StringBuffer(this.getCellphone()).reverse()).substring(1, 17);//生成的ID是11位，补全16位密码
        byte[] decryptResult = CryptionUtils.decryptCustomer(CryptionUtils.parseHexStr2Byte(this.getSign()), password);
        byte[] decode = Base64.decode(decryptResult, Base64.NO_WRAP);
        String[] split = new String(decode).split("-");
        if (split == null || split.length != 3) {
            return false;
        }
        if (TextUtils.equals(split[0], this.getId() + "")
                && System.currentTimeMillis() - Long.parseLong(split[1]) < 60 * 1000
                && TextUtils.equals(split[2], this.getCellphone())
                ) {
            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    private boolean isSessionParamsOk() {
        if (!TextUtils.isEmpty(this.getT())
                && !TextUtils.isEmpty(this.getId() + "")
                && CellPhoneUtils.isCellphone(this.getCellphone())) {
            return true;
        } else {
            return false;
        }
    }

    @JSONField(serialize = false)
    public boolean isParamsOk() {
        return isSessionParamsOk();
    }
}
