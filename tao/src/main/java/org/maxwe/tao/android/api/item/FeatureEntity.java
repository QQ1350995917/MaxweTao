package org.maxwe.tao.android.api.item;

/**
 * Created by Pengwei Ding on 2017-01-17 10:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class FeatureEntity {
    private String attr_key;
    private String attr_value;

    public FeatureEntity() {
        super();
    }

    public String getAttr_key() {
        return attr_key;
    }

    public void setAttr_key(String attr_key) {
        this.attr_key = attr_key;
    }

    public String getAttr_value() {
        return attr_value;
    }

    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }

    @Override
    public String toString() {
        return "FeatureEntity{" +
                "attr_key='" + attr_key + '\'' +
                ", attr_value='" + attr_value + '\'' +
                '}';
    }
}
