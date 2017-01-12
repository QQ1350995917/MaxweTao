package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.account.agent.AgentModel;
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
    private LinkedList<AgentModel> mates;

    public BranchModel() {
        super();
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

    public LinkedList<AgentModel> getMates() {
        return mates;
    }

    public void setMates(LinkedList<AgentModel> mates) {
        this.mates = mates;
    }

    @Override
    public boolean isParamsOk() {
        if (this.getPageIndex() >= 0 && this.getPageSize() > 0) {
            return true && super.isParamsOk();
        }
        return false;
    }
}
