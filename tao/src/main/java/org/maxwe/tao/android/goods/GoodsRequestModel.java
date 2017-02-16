package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-08 11:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsRequestModel extends SessionModel {

    private static final String DES = "_des";//排序（降序）
    private static final String ASC = "_asc";// 排序（升序）
    private static final String TOTAL_SALES = "total_sales"; //销量，
    private static final String TK_RATE = "tk_rate";// 淘客佣金比率
    private static final String TK_TOTAL_SALES = "tk_total_sales";// 累计推广量
    private static final String TK_TOTAL_COMMI = "tk_total_commi";// 总支出佣金

    public static final String TOTAL_SALES_DES = TOTAL_SALES + DES;
    public static final String TOTAL_SALES_ASC = TOTAL_SALES + ASC;
    public static final String TK_RATE_DES = TK_RATE + ASC;
    public static final String TK_RATE_ASC = TK_RATE + ASC;
    public static final String TK_TOTAL_SALES_DES = TK_TOTAL_SALES + ASC;
    public static final String TK_TOTAL_SALES_ASC = TK_TOTAL_SALES + ASC;
    public static final String TK_TOTAL_COMMI_DES = TK_TOTAL_COMMI + ASC;
    public static final String TK_TOTAL_COMMI_ASC = TK_TOTAL_COMMI + ASC;

    // 查询关键字
    private String q;
    // 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi）
    private String sort;// 否
    // 第几页，默认：１
    private int page_no = 1;// 否
    // 页大小，默认20，1~100
    private int page_size = 20;// 否
    // 后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
    private String cat = "0";// 否
    // 所在地
    private String itemloc;// 否
    // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
    private boolean is_tmall = false;// 否
    // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
    private boolean is_overseas = false;// 否
    // 折扣价范围下限，单位：元
    private int start_price = 0000;// 否
    // 折扣价范围上限，单位：元
    private int end_price = 9999;// 否
    // 淘客佣金比率上限，如：1234表示12.34%
    private int start_tk_rate = 0000;// 否
    // 淘客佣金比率下限，如：1234表示12.34%
    private int end_tk_rate = 9999;// 否

    public GoodsRequestModel() {
    }

    public GoodsRequestModel(int page_no, int page_size) {
        this.page_no = page_no;
        this.page_size = page_size;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getItemloc() {
        return itemloc;
    }

    public void setItemloc(String itemloc) {
        this.itemloc = itemloc;
    }

    public boolean is_tmall() {
        return is_tmall;
    }

    public void setIs_tmall(boolean is_tmall) {
        this.is_tmall = is_tmall;
    }

    public boolean is_overseas() {
        return is_overseas;
    }

    public void setIs_overseas(boolean is_overseas) {
        this.is_overseas = is_overseas;
    }

    public int getStart_price() {
        return start_price;
    }

    public void setStart_price(int start_price) {
        this.start_price = start_price;
    }

    public int getEnd_price() {
        return end_price;
    }

    public void setEnd_price(int end_price) {
        this.end_price = end_price;
    }

    public int getStart_tk_rate() {
        return start_tk_rate;
    }

    public void setStart_tk_rate(int start_tk_rate) {
        this.start_tk_rate = start_tk_rate;
    }

    public int getEnd_tk_rate() {
        return end_tk_rate;
    }

    public void setEnd_tk_rate(int end_tk_rate) {
        this.end_tk_rate = end_tk_rate;
    }
}
