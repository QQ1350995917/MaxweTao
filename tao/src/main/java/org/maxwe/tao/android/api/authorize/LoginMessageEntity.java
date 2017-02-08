package org.maxwe.tao.android.api.authorize;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-08 16:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class LoginMessageEntity implements Serializable {
    private boolean isTmallHKSeller;
    private boolean isShowMaterial;
    private String nickname;
    private boolean isB2C;
    private boolean isInCPADebtWhiteList;
    private boolean isJoinedCps;
    private boolean isEarnedSeller;
    private String env;
    private int frozen;
    private String logname;
    private String taobaoNumberID;
    private boolean isEarnedSellerEvaluatePermitted;
    private boolean isShopKeeperOwningMoney;
    private String infoCompleteUrl;
    private boolean isBigShopKeeper;
    private boolean shopKeeperId;
    private boolean isEarnedSellerShopPermitted;
    private String _tb_token_;
    private boolean isInCPAWhiteList;
    private int newItemEventCount;

    public LoginMessageEntity() {
        super();
    }

    public boolean isTmallHKSeller() {
        return isTmallHKSeller;
    }

    public void setTmallHKSeller(boolean tmallHKSeller) {
        isTmallHKSeller = tmallHKSeller;
    }

    public boolean isShowMaterial() {
        return isShowMaterial;
    }

    public void setShowMaterial(boolean showMaterial) {
        isShowMaterial = showMaterial;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isB2C() {
        return isB2C;
    }

    public void setB2C(boolean b2C) {
        isB2C = b2C;
    }

    public boolean isInCPADebtWhiteList() {
        return isInCPADebtWhiteList;
    }

    public void setInCPADebtWhiteList(boolean inCPADebtWhiteList) {
        isInCPADebtWhiteList = inCPADebtWhiteList;
    }

    public boolean isJoinedCps() {
        return isJoinedCps;
    }

    public void setJoinedCps(boolean joinedCps) {
        isJoinedCps = joinedCps;
    }

    public boolean isEarnedSeller() {
        return isEarnedSeller;
    }

    public void setEarnedSeller(boolean earnedSeller) {
        isEarnedSeller = earnedSeller;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getTaobaoNumberID() {
        return taobaoNumberID;
    }

    public void setTaobaoNumberID(String taobaoNumberID) {
        this.taobaoNumberID = taobaoNumberID;
    }

    public boolean isEarnedSellerEvaluatePermitted() {
        return isEarnedSellerEvaluatePermitted;
    }

    public void setEarnedSellerEvaluatePermitted(boolean earnedSellerEvaluatePermitted) {
        isEarnedSellerEvaluatePermitted = earnedSellerEvaluatePermitted;
    }

    public boolean isShopKeeperOwningMoney() {
        return isShopKeeperOwningMoney;
    }

    public void setShopKeeperOwningMoney(boolean shopKeeperOwningMoney) {
        isShopKeeperOwningMoney = shopKeeperOwningMoney;
    }

    public String getInfoCompleteUrl() {
        return infoCompleteUrl;
    }

    public void setInfoCompleteUrl(String infoCompleteUrl) {
        this.infoCompleteUrl = infoCompleteUrl;
    }

    public boolean isBigShopKeeper() {
        return isBigShopKeeper;
    }

    public void setBigShopKeeper(boolean bigShopKeeper) {
        isBigShopKeeper = bigShopKeeper;
    }

    public boolean isShopKeeperId() {
        return shopKeeperId;
    }

    public void setShopKeeperId(boolean shopKeeperId) {
        this.shopKeeperId = shopKeeperId;
    }

    public boolean isEarnedSellerShopPermitted() {
        return isEarnedSellerShopPermitted;
    }

    public void setEarnedSellerShopPermitted(boolean earnedSellerShopPermitted) {
        isEarnedSellerShopPermitted = earnedSellerShopPermitted;
    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
    }

    public boolean isInCPAWhiteList() {
        return isInCPAWhiteList;
    }

    public void setInCPAWhiteList(boolean inCPAWhiteList) {
        isInCPAWhiteList = inCPAWhiteList;
    }

    public int getNewItemEventCount() {
        return newItemEventCount;
    }

    public void setNewItemEventCount(int newItemEventCount) {
        this.newItemEventCount = newItemEventCount;
    }
}
