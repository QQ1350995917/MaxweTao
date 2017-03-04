package org.maxwe.tao.android.level;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-14 12:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class LevelEntity implements Serializable{
    private String id;
    private String name;
    private String description;
    private int minNum;
    private float price;
    private int level;
    private int weight;
    private long createTime;
    private long updateTime;


    public LevelEntity() {
        super();
    }

    public LevelEntity(String name, int minNum){
        this.name = name;
        this.minNum = minNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof LevelEntity){
            if (this.getLevel() == ((LevelEntity)o).getLevel()){
                return true;
            }
            return false;
        }
        return false;
    }
}
