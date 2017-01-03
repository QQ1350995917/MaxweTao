package org.maxwe.tao.android.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.response.Response;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 18:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentManager {

    private static final String URL_EXIST = Constants.DOMAIN + "/agent/exist";
    private static final String URL_SMS = Constants.DOMAIN + "/agent/smsCode";
    private static final String URL_CREATE = Constants.DOMAIN + "/agent/create";
    private static final String URL_LOST = Constants.DOMAIN + "/agent/lost";
    private static final String URL_MODIFY = Constants.DOMAIN + "/agent/password";
    private static final String URL_LOGIN = Constants.DOMAIN + "/agent/login";
    private static final String URL_LOGOUT = Constants.DOMAIN + "/agent/logout";
    private static final String URL_GET_ACCESS = Constants.DOMAIN + "/agent/getAccess";

    public interface OnRequestCallback {
        void onSuccess(Response response);

        void onError(Throwable exception, AgentEntity agentEntity);
    }

    private static Callback.Cancelable request(String url, final AgentEntityInter agentEntity, final OnRequestCallback onCreateCallback) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter(Constants.PARAMS, JSON.toJSONString(agentEntity));
        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                onCreateCallback.onSuccess(JSONObject.parseObject(result, Response.class));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onCreateCallback.onError(ex, agentEntity);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return cancelable;
    }

    public static Callback.Cancelable requestExist(String cellphone, OnRequestCallback onCreateCallback) {
        AgentEntityInter agentEntity = new AgentEntityInter();
        agentEntity.setCellphone(cellphone);
        return request(URL_EXIST, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestSMSCode(String cellphone, OnRequestCallback onCreateCallback) {
        AgentEntityInter agentEntity = new AgentEntityInter();
        agentEntity.setCellphone(cellphone);
        return request(URL_SMS, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestCreate(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_CREATE, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestLost(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_LOST, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestLogin(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_LOGIN, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestLogout(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_LOGOUT, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestModifyPassword(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_MODIFY, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestAccessCode(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback){
        return request(URL_GET_ACCESS, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable createToLocal(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return null;
    }
}
