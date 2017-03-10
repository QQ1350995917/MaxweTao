package org.maxwe.tao.android.trade;


import org.maxwe.tao.android.mate.MateModel;
import org.maxwe.tao.android.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:05.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理之间发生授权码交易的响应模型
 */
public class TradeResponseModel extends ResponseModel<TradeRequestModel> {
    private MateModel branchAgent;
    private int codeNum;
    private float price;
    private float codeDeal;

    public TradeResponseModel() {
        super();
    }

    public TradeResponseModel(MateModel branchAgent,int codeNum) {
        super();
        this.branchAgent = branchAgent;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCodeDeal() {
        return codeDeal;
    }

    public void setCodeDeal(float codeDeal) {
        this.codeDeal = codeDeal;
    }
}
