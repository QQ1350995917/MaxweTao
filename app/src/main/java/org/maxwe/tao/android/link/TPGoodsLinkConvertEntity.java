package org.maxwe.tao.android.link;

import org.maxwe.tao.android.APICommon;

/**
 * Created by Pengwei Ding on 2016-12-24 14:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:淘宝客商品三方分成链接转换
 * http://open.taobao.com/doc2/apiDetail.htm?spm=0.0.0.0.PBdy7Y&apiId=24519&scopeId=11651
 */
public class TPGoodsLinkConvertEntity extends APICommon {
    // 需返回的字段列表
    private String fields; // 是
    // 商品ID串，用','分割，从taobao.tbk.item.get接口获取num_iid字段
    private String num_iids; // 是
    // 三方pid，满足mm_xxx_xxx_xxx格式
    private String sub_pid; // 是
    // 链接形式：1：PC，2：无线，默认：１
    private int platform = 2; // 否
    // 自定义输入串，英文和数字组成，长度不能大于12个字符，区分不同的推广渠道
    private String unid; // 否
    // 广告位ID，区分效果位置
    private int adzone_id; // 是

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getNum_iids() {
        return num_iids;
    }

    public void setNum_iids(String num_iids) {
        this.num_iids = num_iids;
    }

    public String getSub_pid() {
        return sub_pid;
    }

    public void setSub_pid(String sub_pid) {
        this.sub_pid = sub_pid;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public int getAdzone_id() {
        return adzone_id;
    }

    public void setAdzone_id(int adzone_id) {
        this.adzone_id = adzone_id;
    }
}
