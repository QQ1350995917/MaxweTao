package org.maxwe.tao.android.trade;

import org.maxwe.tao.android.account.model.AuthenticateModel;
import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成单个授权码的请求模型
 * 给普通用户使用，此时仅仅是生成码的请求，而不是用户真正使用
 */
public class GrantRequestModel extends AuthenticateModel {
    public GrantRequestModel() {
        super();
    }
    public GrantRequestModel(TokenModel tokenModel) {
        super(tokenModel);
    }
}
