package org.maxwe.tao.android.trade;


import org.maxwe.tao.android.account.model.AuthenticateModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.level.LevelEntity;
import org.maxwe.tao.android.mate.MateModel;

/**
 * Created by Pengwei Ding on 2017-03-05 10:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 提升代理级别的请求模型
 */
public class UpgradeRequestModel extends AuthenticateModel {
    private MateModel branch;//分支代理
    private LevelEntity upgradeToLevel;//要提升到的级别
    private int codeNum;//补码数量

    public UpgradeRequestModel() {
        super();
    }

    public UpgradeRequestModel(TokenModel tokenModel,MateModel mateModel,LevelEntity upgradeToLevel,int codeNum) {
        super(tokenModel);
        this.branch = mateModel;
        this.upgradeToLevel = upgradeToLevel;
        this.codeNum= codeNum;
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }

    public LevelEntity getUpgradeToLevel() {
        return upgradeToLevel;
    }

    public void setUpgradeToLevel(LevelEntity upgradeToLevel) {
        this.upgradeToLevel = upgradeToLevel;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }


}
