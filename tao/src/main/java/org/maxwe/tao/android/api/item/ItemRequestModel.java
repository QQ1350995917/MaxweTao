package org.maxwe.tao.android.api.item;

import org.maxwe.tao.android.api.CommonModel;

import java.util.Arrays;

/**
 * Created by Pengwei Ding on 2017-01-17 10:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ItemRequestModel extends CommonModel {
    private int[] cids;// 商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
    // 需要返回的字段列表，见ItemCat，
    // 默认返回：cid,parent_cid,name,is_parent；
    // 增量类目信息,根据fields传入的参数返回相应的结果。
    // features字段： 1、如果存在attr_key=freeze表示该类目被冻结了，attr_value=0,5，value可能存在2个值（也可能只有1个），用逗号分割，0表示禁编辑，5表示禁止发布
    // 默认值：cid,parent_cid,name,is_parent 最大列表长度：20
    private String[] fields;
    private int parent_cid;// 父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)


    public int[] getCids() {
        return cids;
    }

    public void setCids(int[] cids) {
        this.cids = cids;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public int getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(int parent_cid) {
        this.parent_cid = parent_cid;
    }

    @Override
    public String toString() {
        return "ItemRequestModel{" +
                "parent_cid=" + parent_cid +
                ", fields=" + Arrays.toString(fields) +
                ", cids=" + Arrays.toString(cids) +
                '}';
    }
}
