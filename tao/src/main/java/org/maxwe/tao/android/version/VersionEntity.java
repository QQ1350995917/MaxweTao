package org.maxwe.tao.android.version;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-06 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class VersionEntity implements Serializable {
    private String platform; // ios or android
    private int type; // 1 or 2
    private int versionCode;
    private String versionName;
    private String appName;
    private String information;
    private String url;
    private int upgrade; // 0不强制升级 其他强制
    private String createTime;
    private String updateTime;

    public VersionEntity() {
        super();
    }

    public VersionEntity(String platform,int type) {
        this.platform = platform;
        this.type = type;
    }

    public VersionEntity(String platform,int type,int versionCode) {
        this(platform,type);
        this.versionCode = versionCode;
    }

    public VersionEntity(String platform,int type,int versionCode,String versionName) {
        this(platform,type,versionCode);
        this.versionName = versionName;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VersionEntity) {
            VersionEntity other = (VersionEntity) obj;
            if (TextUtils.equals(this.getPlatform(), other.getPlatform()) &&
                    this.getType() != 0 &&
                    other.getType() != 0 &&
                    this.getType() == other.getType()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "platform = " + platform + " , type = " + type + " , versionCode = " + versionCode + " , versionName = " +
                versionName + " , information = " + information + " , url = " + url + " , upgrade = " + upgrade +
                " , createTime = " + createTime + " , updateTime = " + updateTime;
    }
}
