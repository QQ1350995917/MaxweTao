package org.maxwe.tao.android.response;

/**
 * Created by Pengwei Ding on 2016-12-30 14:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class Response implements IResponse {
    private int code;
    private String message = "this is tip message!";
    private String data;

    public Response() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
