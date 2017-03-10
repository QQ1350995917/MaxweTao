package org.maxwe.tao.android.mate;

import org.maxwe.tao.android.level.LevelEntity;
import org.maxwe.tao.android.response.ResponseModel;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-03 17:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理查看下级信息的响应模型
 */
public class BranchInfoResponseModel extends ResponseModel<BranchInfoRequestModel> {
    private MateModel branch;
    private List<LevelEntity> levels;

    public BranchInfoResponseModel() {
        super();
    }

    public BranchInfoResponseModel(MateModel branch) {
        super();
        this.branch = branch;
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }

    public List<LevelEntity> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelEntity> levels) {
        this.levels = levels;
    }
}
