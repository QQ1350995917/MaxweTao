package org.maxwe.tao.android.api.item;

import java.util.Arrays;

/**
 * Created by Pengwei Ding on 2017-01-17 10:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ItemEntity {

    private int cid;
    private int parent_cid;
    private String name;
    private boolean is_parent;
    private String status;
    private int sort_order;
    private FeatureEntity[] features;
    private boolean taosir_cat;

    public ItemEntity() {
        super();
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(int parent_cid) {
        this.parent_cid = parent_cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_parent() {
        return is_parent;
    }

    public void setIs_parent(boolean is_parent) {
        this.is_parent = is_parent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public FeatureEntity[] getFeatures() {
        return features;
    }

    public void setFeatures(FeatureEntity[] features) {
        this.features = features;
    }

    public boolean isTaosir_cat() {
        return taosir_cat;
    }

    public void setTaosir_cat(boolean taosir_cat) {
        this.taosir_cat = taosir_cat;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "cid=" + cid +
                ", parent_cid=" + parent_cid +
                ", name='" + name + '\'' +
                ", is_parent=" + is_parent +
                ", status='" + status + '\'' +
                ", sort_order=" + sort_order +
                ", features=" + Arrays.toString(features) +
                ", taosir_cat=" + taosir_cat +
                '}';
    }
}
