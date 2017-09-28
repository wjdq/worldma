package com.hong.worldma.entity.wx;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
public class WxSyncKey {

    public Integer Count;
    public List<SyncKeyMap> List;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public java.util.List<SyncKeyMap> getList() {
        return List;
    }

    public void setList(java.util.List<SyncKeyMap> list) {
        List = list;
    }
}