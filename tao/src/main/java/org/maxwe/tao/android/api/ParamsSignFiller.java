package org.maxwe.tao.android.api;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * Created by Pengwei Ding on 2017-01-08 12:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ParamsSignFiller implements PropertyFilter {
    @Override
    public boolean apply(Object object, String name, Object value) {
        if ("sign".equals(name)) {
            return false;
        }
        return true;
    }
}
