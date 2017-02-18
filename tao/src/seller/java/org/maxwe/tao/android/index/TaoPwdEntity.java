package org.maxwe.tao.android.index;

/**
 * Created by Pengwei Ding on 2017-02-18 15:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TaoPwdEntity {
    private String title;
    private String pwd;
    private String price;
    private String desc;

    public TaoPwdEntity() {
        super();
    }

    public TaoPwdEntity(String title, String pwd, String price, String desc) {
        this.title = title;
        this.pwd = pwd;
        this.price = price;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
