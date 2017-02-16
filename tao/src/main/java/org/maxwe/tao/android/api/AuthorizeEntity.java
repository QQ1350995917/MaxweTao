package org.maxwe.tao.android.api;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-22 15:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AuthorizeEntity implements Serializable {

    private String content;
    private String access_token;// Access token
    private String token_type;// Access token的类型目前只支持bearer
    private String expires_in;// Access token过期时间 单位s
    private String refresh_token;// Refresh token，可用来刷新access_token
    private String re_expires_in;// Refresh token过期时间 单位s
    private String r1_expires_in;// r1级别API或字段的访问过期时间； 单位s
    private String r2_expires_in;// r2级别API或字段的访问过期时间； 单位s
    private String taobao_user_id;// 淘宝帐号对应id
    private String taobao_user_nick;// 淘宝账号
    private String w1_expires_in;// w1级别API或字段的访问过期时间； 单位s
    private String w2_expires_in;// w2级别API或字段的访问过期时间； 单位s
    private String state;// 维持应用的状态，传入值与返回值保持一致。
    private String top_sign;//
    private long createTime;

    public AuthorizeEntity() {
        super();
    }

    public AuthorizeEntity(String content) {
        this.content = content;
        this.setCreateTime(System.currentTimeMillis());
        this.build();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRe_expires_in() {
        return re_expires_in;
    }

    public void setRe_expires_in(String re_expires_in) {
        this.re_expires_in = re_expires_in;
    }

    public String getR1_expires_in() {
        return r1_expires_in;
    }

    public void setR1_expires_in(String r1_expires_in) {
        this.r1_expires_in = r1_expires_in;
    }

    public String getR2_expires_in() {
        return r2_expires_in;
    }

    public void setR2_expires_in(String r2_expires_in) {
        this.r2_expires_in = r2_expires_in;
    }

    public String getTaobao_user_id() {
        return taobao_user_id;
    }

    public void setTaobao_user_id(String taobao_user_id) {
        this.taobao_user_id = taobao_user_id;
    }

    public String getTaobao_user_nick() {
        return taobao_user_nick;
    }

    public void setTaobao_user_nick(String taobao_user_nick) {
        this.taobao_user_nick = taobao_user_nick;
    }

    public String getW1_expires_in() {
        return w1_expires_in;
    }

    public void setW1_expires_in(String w1_expires_in) {
        this.w1_expires_in = w1_expires_in;
    }

    public String getW2_expires_in() {
        return w2_expires_in;
    }

    public void setW2_expires_in(String w2_expires_in) {
        this.w2_expires_in = w2_expires_in;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTop_sign() {
        return top_sign;
    }

    public void setTop_sign(String top_sign) {
        this.top_sign = top_sign;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void build() {
        if (this.content != null) {
            String hash = this.content.substring(this.content.indexOf("#"));
            String[] split = hash.split("&");
            for (String string : split) {
                String[] keyValue = string.split("=");
                String key = keyValue[0];
                String value = keyValue[1];
                if ("access_token".equals(key)) {
                    this.setAccess_token(value);
                } else if ("token_type".equals(key)) {
                    this.setToken_type(value);
                } else if ("expires_in".equals(key)) {
                    this.setExpires_in(value);
                } else if ("refresh_token".equals(key)) {
                    this.setRefresh_token(value);
                } else if ("re_expires_in".equals(key)) {
                    this.setRe_expires_in(value);
                } else if ("r1_expires_in".equals(key)) {
                    this.setR1_expires_in(value);
                } else if ("r2_expires_in".equals(key)) {
                    this.setR2_expires_in(value);
                } else if ("taobao_user_id".equals(key)) {
                    this.setTaobao_user_id(key);
                } else if ("taobao_user_nick".equals(key)) {
                    this.setTaobao_user_nick(value);
                } else if ("w1_expires_in".equals(key)) {
                    this.setW1_expires_in(value);
                } else if ("w2_expires_in".equals(key)) {
                    this.setW2_expires_in(value);
                } else if ("state".equals(key)) {
                    this.setState(value);
                } else if ("top_sign".equals(key)) {
                    this.setTop_sign(value);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AuthorizeEntity{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", re_expires_in='" + re_expires_in + '\'' +
                ", r1_expires_in='" + r1_expires_in + '\'' +
                ", r2_expires_in='" + r2_expires_in + '\'' +
                ", taobao_user_id='" + taobao_user_id + '\'' +
                ", taobao_user_nick='" + taobao_user_nick + '\'' +
                ", w1_expires_in='" + w1_expires_in + '\'' +
                ", w2_expires_in='" + w2_expires_in + '\'' +
                ", state='" + state + '\'' +
                ", top_sign='" + top_sign + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }


}
