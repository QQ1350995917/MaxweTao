package org.maxwe.tao.android.meta;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-09 22:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取验证码的请求模型
 */
public class SMSCodeRequestModel extends TokenModel {
    public SMSCodeRequestModel() {
        super();
    }

    public SMSCodeRequestModel(String cellphone,int apt) {
        super(null, 0, cellphone,apt);
    }

    public boolean isSMSCodeRequestParamsOk(){
        return super.isCellphoneParamsOk();
    }
}
