package org.maxwe.tao.android.account.user;

import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.utils.StringUtils;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新申请加入推广计划的理由的请求模型
 */
public class UpdateReasonRequestModel extends TokenModel {
    private String reason;

    public UpdateReasonRequestModel() {
        super();
    }

    public UpdateReasonRequestModel(TokenModel tokenModel, String reason) {
        super(tokenModel);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isReasonParamsOk() {
        if (StringUtils.isEmpty(this.getReason())){
            return false;
        }
        if (this.getReason().length() < 4 || this.getReason().length() > 200){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
