package org.maxwe.tao.android.meta;

import org.maxwe.tao.android.utils.CellPhoneUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 18:43.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SMSModel implements Serializable {
    private String cellphone;

    public SMSModel() {
        super();
    }

    public SMSModel(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
