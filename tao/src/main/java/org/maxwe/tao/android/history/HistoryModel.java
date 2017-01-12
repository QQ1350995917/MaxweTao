package org.maxwe.tao.android.history;

import org.maxwe.tao.android.account.model.SessionModel;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-11 17:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class HistoryModel extends SessionModel {
    private int total; // 响应字段
    private int pageIndex; // 请求字段
    private int pageSize; // 请求字段

    private LinkedList<HistoryEntity> historyEntities; // 响应字段

    public HistoryModel() {
        super();
    }

    public HistoryModel(SessionModel sessionModel,int pageIndex,int pageSize) {
        super(sessionModel.getT(),sessionModel.getMark(),sessionModel.getCellphone());
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

    public LinkedList<HistoryEntity> getHistoryEntities() {
        return historyEntities;
    }

    public void setHistoryEntities(LinkedList<HistoryEntity> historyEntities) {
        this.historyEntities = historyEntities;
    }
}
