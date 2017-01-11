package org.maxwe.tao.android.account.agent;

import android.text.TextUtils;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-10 17:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class FindModel  extends SessionModel {
    private String targetMark;

    public FindModel() {
        super();
    }

    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
    }

    @Override
    public String toString() {
        return super.toString() + "FindModel{" +
                "targetMark='" + targetMark + '\'' +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        if (TextUtils.isEmpty(this.getTargetMark())){
            return false;
        }
        return super.isParamsOk();
    }
}
