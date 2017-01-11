package org.maxwe.tao.android.account;

import org.maxwe.tao.android.utils.DateTimeUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class CSEntity implements Serializable {
    private String id;
    private String cellphone;
    private String token;
    private long timestamp;

    public CSEntity() {
        super();
    }

    public CSEntity(String id,String cellphone,String token) {
        super();
        this.id = id;
        this.cellphone = cellphone;
        this.token = token;
        this.timestamp = DateTimeUtils.getCurrentTimestamp();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void resetTimestamp(){
        this.setTimestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "CSEntity{" +
                "timestamp=" + timestamp +
                ", token='" + token + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
