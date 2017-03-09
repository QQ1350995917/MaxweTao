package org.maxwe.tao.android.goods.alimama;

import com.alibaba.fastjson.annotation.JSONField;

import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.utils.StringUtils;

/**
 * Created by Pengwei Ding on 2017-03-08 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广以及其下的推广位列表请求模型
 */
public class BrandListRequestModel extends TokenModel {
    private String cookie;

    public BrandListRequestModel() {
        super();
    }

    public BrandListRequestModel(TokenModel tokenModel, String cookie) {
        super(tokenModel);
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @JSONField(serialize=false)
    public boolean isBrandListRequestParamsOk(){
        if (StringUtils.isEmpty(this.getCookie())){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
