package org.maxwe.tao.android.history;


import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.utils.DateTimeUtils;

/**
 * Created by Pengwei Ding on 2017-03-04 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理返点请求模型
 */
public class RebateRequestModel extends TokenModel {
    private int year;//年份
    private int month;//月份
    private int monthCounter;//月份数量

    public RebateRequestModel() {
        super();
    }

    public RebateRequestModel(TokenModel tokenModel,int year,int month,int monthCounter) {
        super(tokenModel.getT(),tokenModel.getId(),tokenModel.getCellphone(),tokenModel.getApt());
        this.year = year;
        this.month = month;
        this.monthCounter = monthCounter;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonthCounter() {
        return monthCounter;
    }

    public void setMonthCounter(int monthCounter) {
        this.monthCounter = monthCounter;
    }

}
