package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-09 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取枝干下所有分支的请求模型
 */
public class BranchesRequestModel extends TokenModel {
    private int pageIndex;
    private int pageSize;

    public BranchesRequestModel() {
        super();
    }

    public BranchesRequestModel(TokenModel tokenModel,int pageIndex,int pageSize) {
        super(tokenModel.getT(),tokenModel.getId(),tokenModel.getCellphone(),tokenModel.getApt());
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isParamsOk() {
        return this.getPageIndex() >= 0 && this.getPageSize() > 0 && super.isTokenParamsOk();
    }
}
