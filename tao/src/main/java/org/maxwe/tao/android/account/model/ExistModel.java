package org.maxwe.tao.android.account.model;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ExistModel implements Serializable {
    private String cellphone;

    public ExistModel() {
        super();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @JSONField(serialize=false)
    public boolean isParamsOk(){
        if (!TextUtils.isEmpty(this.getCellphone())){
            return true;
        }
        return false;
    }
}
