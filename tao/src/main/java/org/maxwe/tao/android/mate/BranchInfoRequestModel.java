package org.maxwe.tao.android.mate;


import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 17:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理查看下级信息的请求模型
 */
public class BranchInfoRequestModel extends TokenModel {
    private int branchId;

    public BranchInfoRequestModel() {
        super();
    }

    public BranchInfoRequestModel(TokenModel tokenModel,int branchId) {
        super(tokenModel.getT(),tokenModel.getId(),tokenModel.getCellphone(),tokenModel.getApt());
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isBranchInfoParamsOk(){
        return super.isTokenParamsOk() && this.getBranchId() > 0;
    }

}
