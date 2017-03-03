package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-03 16:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 向下级授权的请求模型
 */
public class GrantBranchRequestModel extends TokenModel {
    private int branchId;

    public GrantBranchRequestModel() {
        super();
    }

    public GrantBranchRequestModel(TokenModel tokenModel,int branchId) {
        super(tokenModel.getT(),tokenModel.getId(),tokenModel.getCellphone(),tokenModel.getApt());
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isGrantBranchParamsOk(){
        return super.isTokenParamsOk() && this.getBranchId() > 0;
    }
}
