package org.maxwe.tao.android.trade;


import org.maxwe.tao.android.account.model.AuthenticateModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.mate.MateModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理之间发生授权码交易的请求模型
 */
public class TradeRequestModel extends AuthenticateModel {
    private MateModel branchAgent;
    private int codeNum;

    public TradeRequestModel() {
        super();
    }

    public TradeRequestModel(TokenModel tokenModel,MateModel mateModel,int codeNum) {
        super(tokenModel);
        this.branchAgent = mateModel;
        this.codeNum = codeNum;
    }

    public MateModel getBranchAgent() {
        return branchAgent;
    }

    public void setBranchAgent(MateModel branchAgent) {
        this.branchAgent = branchAgent;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }


}
