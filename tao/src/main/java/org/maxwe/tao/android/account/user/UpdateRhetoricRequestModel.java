package org.maxwe.tao.android.account.user;

import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.utils.StringUtils;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新分享推广说辞的请求模型
 */
public class UpdateRhetoricRequestModel extends TokenModel {
    private String rhetoric;

    public UpdateRhetoricRequestModel() {
        super();
    }

    public UpdateRhetoricRequestModel(TokenModel tokenModel, String rhetoric) {
        super(tokenModel);
        this.rhetoric = rhetoric;
    }

    public String getRhetoric() {
        return rhetoric;
    }

    public void setRhetoric(String rhetoric) {
        this.rhetoric = rhetoric;
    }

    public boolean isRhetoricParamsOk() {
        if (StringUtils.isEmpty(this.getRhetoric())) {
            return false;
        }
        if (this.getRhetoric().length() > 100) {
            return false;
        }
        return super.isTokenParamsOk();
    }
}
