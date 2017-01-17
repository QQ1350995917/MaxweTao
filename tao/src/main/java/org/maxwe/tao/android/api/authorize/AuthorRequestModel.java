package org.maxwe.tao.android.api.authorize;

import org.maxwe.tao.android.api.IModel;

/**
 * Created by Pengwei Ding on 2017-01-17 14:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户授权请求模型
 */
public class AuthorRequestModel implements IModel {
    private String client_id;// 等同于appkey
    private final String response_type = "token";//
    private int state;
    private final String view = "wap";

    public AuthorRequestModel() {
        super();
    }

    public AuthorRequestModel(String client_id, int state) {
        this.client_id = client_id;
        this.state = state;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getResponse_type() {
        return response_type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getView() {
        return view;
    }

    @Override
    public String toString() {
        return "AuthorRequestModel{" +
                "view='" + view + '\'' +
                ", state=" + state +
                ", response_type='" + response_type + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }
}
