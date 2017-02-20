package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.response.IResponse;

/**
 * Created by Pengwei Ding on 2017-02-11 13:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsResponseResults implements IResponse {
    private GoodsResponseResultsEntity results;
    private String request_id;
    private int total_results;

    public GoodsResponseResults() {
        super();
    }

    public GoodsResponseResultsEntity getResults() {
        return results;
    }

    public void setResults(GoodsResponseResultsEntity results) {
        this.results = results;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

}
