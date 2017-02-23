package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.response.IResponse;

/**
 * Created by Pengwei Ding on 2017-02-22 21:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TaoConvertResponseModel implements IResponse{

    private String sclick;
    private String taoToken;
    private String qrCodeUrl;
    private String shortLinkUrl;

    public TaoConvertResponseModel() {
        super();
    }

    public String getSclick() {
        return sclick;
    }

    public void setSclick(String sclick) {
        this.sclick = sclick;
    }

    public String getTaoToken() {
        return taoToken;
    }

    public void setTaoToken(String taoToken) {
        this.taoToken = taoToken;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getShortLinkUrl() {
        return shortLinkUrl;
    }

    public void setShortLinkUrl(String shortLinkUrl) {
        this.shortLinkUrl = shortLinkUrl;
    }

}
