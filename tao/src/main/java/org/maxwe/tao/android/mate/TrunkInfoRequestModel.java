package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 15:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取上级代理的请求模型
 */
public class TrunkInfoRequestModel extends TokenModel {
    public TrunkInfoRequestModel() {
        super();
    }

    public TrunkInfoRequestModel(TokenModel tokenModel) {
        super(tokenModel.getT(),tokenModel.getId(),tokenModel.getCellphone(),tokenModel.getApt());
    }
}
