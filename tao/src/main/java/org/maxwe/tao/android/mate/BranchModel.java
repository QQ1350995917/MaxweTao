package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-12 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class BranchModel extends SessionModel {
    private int total;
    private int pageIndex;
    private int pageSize;
    private LinkedList<AgentEntity> agentEntities;

    public BranchModel() {
        super();
    }

    public BranchModel(SessionModel sessionModel,int pageIndex,int pageSize) {
        super(sessionModel.getT(),sessionModel.getMark(),sessionModel.getCellphone(),sessionModel.getApt());
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public LinkedList<AgentEntity> getAgentEntities() {
        return agentEntities;
    }

    public void setAgentEntities(LinkedList<AgentEntity> agentEntities) {
        this.agentEntities = agentEntities;
    }

    @Override
    public boolean isParamsOk() {
        if (this.getPageIndex() >= 0 && this.getPageSize() > 0) {
            return true && super.isParamsOk();
        }
        return false;
    }
}
