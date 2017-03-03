package org.maxwe.tao.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.api.AuthorizeEntity;
import org.maxwe.tao.android.api.Position;

/**
 * Created by Pengwei Ding on 2017-01-11 10:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SharedPreferencesUtils {
    private static final String KEY_SHARED_PREFERENCES_NAME = "ACCOUNT_SHARED_PREFERENCES_NAME";
    private static final String KEY_LAST_LOGIN_CELLPHONE = "LAST_LOGIN_CELLPHONE";
    private static final String KEY_LOGIN_T = "LOGIN_T";
    private static final String KEY_LOGIN_ID = "LOGIN_ID";
    private static final String KEY_LOGIN_CELLPHONE = "LOGIN_CELLPHONE";
    private static final String KEY_TAO_AUTHOR = "TAO_AUTHOR";
    private static final String KEY_TAO_AUTHOR_CREATE_TIME = "KEY_TAO_AUTHOR_CREATE_TIME";

    private static final String KEY_TAO_PP = "KEY_TAO_PP";
    private static final String KEY_TAO_KEEPER_ID = "KEY_TAO_KEEPER_ID";

    public static String getLastLoginCellphone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String lastAccount = sharedPreferences.getString(KEY_LAST_LOGIN_CELLPHONE, null);
        return lastAccount;
    }

    public static void saveLastLoginCellphone(Context context, String cellphone) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_LAST_LOGIN_CELLPHONE, cellphone);
        edit.commit();
    }

    public static TokenModel getSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String t = sharedPreferences.getString(KEY_LOGIN_T, null);
        int id = sharedPreferences.getInt(KEY_LOGIN_ID, 0);
        String cellphone = sharedPreferences.getString(KEY_LOGIN_CELLPHONE, null);
        TokenModel sessionModel = new TokenModel(t, id, cellphone, context.getResources().getInteger(R.integer.integer_app_type));
        boolean paramsOk = sessionModel.isTokenParamsOk();
        if (paramsOk) {
            return sessionModel;
        } else {
            clearSession(context);
            clearAuthor(context);
            return null;
        }
    }

    public static void saveSession(Context context, TokenModel sessionModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_LOGIN_T, sessionModel.getT());
        edit.putInt(KEY_LOGIN_ID, sessionModel.getId());
        edit.putString(KEY_LOGIN_CELLPHONE, sessionModel.getCellphone());
        edit.commit();
    }

    public static void clearSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_LOGIN_T);
        edit.remove(KEY_LOGIN_ID);
        edit.remove(KEY_LOGIN_CELLPHONE);
        edit.commit();
    }

    public static AuthorizeEntity getAuthor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String content = sharedPreferences.getString(KEY_TAO_AUTHOR, null);
        long createTime = sharedPreferences.getLong(KEY_TAO_AUTHOR_CREATE_TIME, 0L);
        if (content == null) {
            return null;
        }
        AuthorizeEntity authorizeEntity = new AuthorizeEntity(content);
        authorizeEntity.setCreateTime(createTime);
        return authorizeEntity;
    }

    public static void saveAuthor(Context context, AuthorizeEntity authorizeEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_TAO_AUTHOR, authorizeEntity.getContent());
        edit.putLong(KEY_TAO_AUTHOR_CREATE_TIME, authorizeEntity.getCreateTime());
        edit.commit();
    }

    public static void clearAuthor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_TAO_AUTHOR);
        edit.remove(KEY_TAO_AUTHOR_CREATE_TIME);
        edit.commit();
    }


    public static void saveCurrentPP(Context context, Position position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_TAO_PP, position.toJsonString());
        edit.commit();
    }

    public static Position getCurrentPP(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String pp = sharedPreferences.getString(KEY_TAO_PP, null);
        if (pp != null) {
            return JSON.parseObject(pp, Position.class);
        }
        return null;
    }

    public static void clearCurrentPP(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_TAO_PP);
        edit.commit();
    }

    public static void saveCurrentKeeperId(Context context, String keeperId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_TAO_KEEPER_ID, keeperId);
        edit.commit();
    }

    public static String getCurrentKeeperId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String pid = sharedPreferences.getString(KEY_TAO_KEEPER_ID, null);
        return pid;
    }

    public static void clearCurrentKeeperId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_TAO_KEEPER_ID);
        edit.commit();
    }

    public static void onExistClear(Context context) {
        clearCurrentPP(context);
        clearCurrentKeeperId(context);
    }
}
