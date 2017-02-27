package org.maxwe.tao.android.account;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-10 17:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AccountEntity implements Serializable{
    private int id; // 用户ID
    private String cellphone; //手机号码 唯一
    private String password; // 登录密码
    private String name; // 用户备注名字 数据库可为空
    private int status; // 状态，0禁用，1正常，数据库默认为1
    private long createTime; // 创建时间
    private long updateTime; // 更新时间

    public AccountEntity() {
        super();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                ", cellphone='" + cellphone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
