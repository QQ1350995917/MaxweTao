package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.APICommon;

/**
 * Created by Pengwei Ding on 2016-12-24 15:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsEntity extends APICommon {
    // 需返回的字段列表
    private String fields; // 是
    // 查询词
    private String q;// 否
    // 后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
    private String cat;// 否
    // 所在地
    private String itemloc;// 否
    // 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi）
    private String sort;// 否
    // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
    private String is_tmall;// 否
    // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
    private String is_overseas;// 否
    // 折扣价范围下限，单位：元
    private String start_price;// 否
    // 折扣价范围上限，单位：元
    private String end_price;// 否
    // 淘客佣金比率上限，如：1234表示12.34%
    private String start_tk_rate;// 否
    // 淘客佣金比率下限，如：1234表示12.34%
    private String end_tk_rate;// 否
    // 链接形式：1：PC，2：无线，默认：１
    private String platform;// 否
    // 第几页，默认：１
    private String page_no;// 否
    // 页大小，默认20，1~100
    private String page_size;// 否

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIs_tmall() {
        return is_tmall;
    }

    public void setIs_tmall(String is_tmall) {
        this.is_tmall = is_tmall;
    }

    public String getIs_overseas() {
        return is_overseas;
    }

    public void setIs_overseas(String is_overseas) {
        this.is_overseas = is_overseas;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }

    public String getEnd_price() {
        return end_price;
    }

    public void setEnd_price(String end_price) {
        this.end_price = end_price;
    }

    public String getStart_tk_rate() {
        return start_tk_rate;
    }

    public void setStart_tk_rate(String start_tk_rate) {
        this.start_tk_rate = start_tk_rate;
    }

    public String getEnd_tk_rate() {
        return end_tk_rate;
    }

    public void setEnd_tk_rate(String end_tk_rate) {
        this.end_tk_rate = end_tk_rate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPage_no() {
        return page_no;
    }

    public void setPage_no(String page_no) {
        this.page_no = page_no;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }
}
