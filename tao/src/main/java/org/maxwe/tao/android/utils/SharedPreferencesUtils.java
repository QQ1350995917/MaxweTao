package org.maxwe.tao.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-11 10:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SharedPreferencesUtils {
    private static final String KEY_SHARED_PREFERENCES_NAME = "ACCOUNT_SHARED_PREFERENCES_NAME";
    private static final String KEY_LAST_LOGIN_CELLPHONE = "LAST_LOGIN_CELLPHONE";
    private static final String KEY_LOGIN_T = "LOGIN_T";
    private static final String KEY_LOGIN_MARK = "LOGIN_MARK";
    private static final String KEY_LOGIN_CELLPHONE = "LOGIN_CELLPHONE";

    public static String getLastLoginCellphone(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        String lastAccount = sharedPreferences.getString(KEY_LAST_LOGIN_CELLPHONE, null);
        return lastAccount;
    }

    public static void saveLastLoginCellphone(Context context,String cellphone){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_LAST_LOGIN_CELLPHONE, cellphone);
        edit.commit();
    }

    public static SessionModel getSession(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String t = sharedPreferences.getString(KEY_LOGIN_T, null);
        String mark = sharedPreferences.getString(KEY_LOGIN_MARK, null);
        String cellphone = sharedPreferences.getString(KEY_LOGIN_CELLPHONE, null);
        SessionModel sessionModel = new SessionModel(t,mark,cellphone,context.getResources().getInteger(R.integer.integer_app_type));
        boolean paramsOk = sessionModel.isParamsOk();
        if (paramsOk){
            return sessionModel;
        }else{
            clearSession(context);
            return null;
        }
    }

    public static void saveSession(Context context,SessionModel sessionModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(KEY_LOGIN_T, sessionModel.getT());
        edit.putString(KEY_LOGIN_MARK, sessionModel.getMark());
        edit.putString(KEY_LOGIN_CELLPHONE, sessionModel.getCellphone());
        edit.commit();
    }

    public static void clearSession(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(KEY_LOGIN_T);
        edit.remove(KEY_LOGIN_MARK);
        edit.remove(KEY_LOGIN_CELLPHONE);
        edit.commit();
    }
}
