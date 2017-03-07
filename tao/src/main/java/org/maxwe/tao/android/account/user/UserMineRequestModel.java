package org.maxwe.tao.android.account.user;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-07 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户获取自己信息的请求模型
 */
public class UserMineRequestModel extends TokenModel {
    public UserMineRequestModel() {
        super();
    }

    public UserMineRequestModel(TokenModel tokenModel) {
        super(tokenModel);
    }
}
