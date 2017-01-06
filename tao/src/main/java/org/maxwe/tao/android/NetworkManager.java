package org.maxwe.tao.android;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 18:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class NetworkManager {

    private static final String URL_EXIST = Constants.DOMAIN + "/agent/exist";
    private static final String URL_SMS = Constants.DOMAIN + "/agent/smsCode";
    private static final String URL_CREATE = Constants.DOMAIN + "/agent/create";
    private static final String URL_LOST = Constants.DOMAIN + "/agent/lost";
    private static final String URL_MODIFY = Constants.DOMAIN + "/agent/password";
    private static final String URL_LOGIN = Constants.DOMAIN + "/agent/login";
    private static final String URL_LOGOUT = Constants.DOMAIN + "/agent/logout";
    private static final String URL_AGENT = Constants.DOMAIN + "/agent/agent";//获取自己的信息

    private static final String URL_GRANT = Constants.DOMAIN + "/bus/grant";//授权
    private static final String URL_AGENT_BUS = Constants.DOMAIN + "/bus/agent";//授权码交易前的检索
    private static final String URL_TRADE = Constants.DOMAIN + "/bus/trade";//授权码交易
    private static final String URL_AGENTS = Constants.DOMAIN + "/bus/agents";//获取下属代理

    private static final String URL_VERSION = Constants.DOMAIN + "/version/version";

    public interface OnRequestCallback {

        void onSuccess(Response response);

        void onError(Throwable exception, Object object);
    }

    private static Callback.Cancelable request(String url, final Object object, final OnRequestCallback onCreateCallback) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter(Constants.PARAMS, JSON.toJSONString(object));
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onCreateCallback.onSuccess(JSONObject.parseObject(result, Response.class));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                onCreateCallback.onError(ex, object);
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

    public static Callback.Cancelable requestAgent(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_AGENT, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestGrant(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_GRANT, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestAgentBus(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_AGENT_BUS, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestTrade(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_TRADE, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestAgents(AgentEntityInter agentEntity, OnRequestCallback onCreateCallback) {
        return request(URL_AGENTS, agentEntity, onCreateCallback);
    }

    public static Callback.Cancelable requestNewVersion(VersionEntity versionEntity, OnRequestCallback onCreateCallback) {
        return request(URL_VERSION, versionEntity, onCreateCallback);
    }
}
