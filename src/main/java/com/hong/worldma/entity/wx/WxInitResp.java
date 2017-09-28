package com.hong.worldma.entity.wx;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
public class WxInitResp {
    public BaseResponse BaseResponse;
    public int Count;
    public List<Contact> ContactList;
    public WxSyncKey SyncKey;
    public Contact User;

    public com.hong.worldma.entity.wx.BaseResponse getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(com.hong.worldma.entity.wx.BaseResponse baseResponse) {
        BaseResponse = baseResponse;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public List<Contact> getContactList() {
        return ContactList;
    }

    public void setContactList(List<Contact> contactList) {
        ContactList = contactList;
    }

    public Contact getUser() {
        return User;
    }

    public void setUser(Contact user) {
        User = user;
    }

    public WxSyncKey getSyncKey() {
        return SyncKey;
    }

    public void setSyncKey(WxSyncKey syncKey) {
        SyncKey = syncKey;
    }
}
