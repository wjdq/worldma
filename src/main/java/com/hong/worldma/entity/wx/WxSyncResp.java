package com.hong.worldma.entity.wx;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-11
 */
public class WxSyncResp {
    public BaseResponse BaseResponse;
    public int AddMsgCount;
    public int ModContactCount;
    public int DelContactCount;
    public WxSyncKey SyncKey;
    public WxSyncKey SyncCheckKey;

    public com.hong.worldma.entity.wx.BaseResponse getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(com.hong.worldma.entity.wx.BaseResponse baseResponse) {
        BaseResponse = baseResponse;
    }

    public int getAddMsgCount() {
        return AddMsgCount;
    }

    public void setAddMsgCount(int addMsgCount) {
        AddMsgCount = addMsgCount;
    }

    public int getModContactCount() {
        return ModContactCount;
    }

    public void setModContactCount(int modContactCount) {
        ModContactCount = modContactCount;
    }

    public int getDelContactCount() {
        return DelContactCount;
    }

    public void setDelContactCount(int delContactCount) {
        DelContactCount = delContactCount;
    }

    public WxSyncKey getSyncKey() {
        return SyncKey;
    }

    public void setSyncKey(WxSyncKey syncKey) {
        SyncKey = syncKey;
    }

    public WxSyncKey getSyncCheckKey() {
        return SyncCheckKey;
    }

    public void setSyncCheckKey(WxSyncKey syncCheckKey) {
        SyncCheckKey = syncCheckKey;
    }
}
