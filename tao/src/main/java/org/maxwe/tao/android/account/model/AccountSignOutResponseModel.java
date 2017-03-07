package org.maxwe.tao.android.account.model;

import org.maxwe.tao.android.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 13:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 注册请求模型
 */
public class AccountSignOutResponseModel extends ResponseModel<AccountSignOutRequestModel> {
    public AccountSignOutResponseModel() {
        super();
    }

    public AccountSignOutResponseModel(AccountSignOutRequestModel requestModel) {
        super(requestModel);
    }
}
