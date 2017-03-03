package org.maxwe.tao.android.mate;

import com.alibaba.fastjson.annotation.JSONField;

import org.maxwe.tao.android.account.model.AuthenticateModel;
import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 15:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理向上级发起授权请求模型
 */
public class BranchBegRequestModel extends AuthenticateModel {
    private int trunkId;//上级的ID
    private String weChat;//本人的微信号码

    public BranchBegRequestModel() {
        super();
    }

    public BranchBegRequestModel(TokenModel tokenModel,int trunkId,String weChat) {
        super(tokenModel);
        this.trunkId = trunkId;
        this.weChat = weChat;
    }

    public int getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(int trunkId) {
        this.trunkId = trunkId;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @JSONField(serialize = false)
    public BranchBegResponseModel getBranchBegResponseModel(){
        BranchBegResponseModel branchBegResponseModel = new BranchBegResponseModel();
        branchBegResponseModel.setTrunkId(this.getTrunkId());
        branchBegResponseModel.setWeChat(this.getWeChat());
        return branchBegResponseModel;
    }
}
