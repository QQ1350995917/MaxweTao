package org.maxwe.tao.android.mate;


import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-03 15:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理向上级发起授权请求的响应模型
 */
public class BranchBegResponseModel extends ResponseModel<BranchBegRequestModel> {
    private MateModel trunk;
    private AgentEntity branch;

    public BranchBegResponseModel() {
        super();
    }

    public MateModel getTrunk() {
        return trunk;
    }

    public void setTrunk(MateModel trunk) {
        this.trunk = trunk;
    }

    public AgentEntity getBranch() {
        return branch;
    }

    public void setBranch(AgentEntity branch) {
        this.branch = branch;
    }
}
