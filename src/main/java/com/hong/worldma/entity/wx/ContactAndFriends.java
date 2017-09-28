package com.hong.worldma.entity.wx;


import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.entity.wm.ClientFriends;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  存放处理过后的客户、客户的好友和信息发送状态
 * @Author: hong
 * @Date: 2017-09-08
 */
public class ContactAndFriends {
    //扫码客户
    private Client client;
    //扫码客户好友总数量
    private int memberCount;
    //被过滤之后扫码客户好友列表
    private List<Contact> memberList = new ArrayList<>();
    //被过滤之后扫码客户群组列表
    private List<Contact> groupList = new ArrayList<>();
    //被过滤之后扫码客户公众号列表
    private List<Contact> publicList = new ArrayList<>();
    //发送消息成功的好友列表
    private List<ClientFriends> sendMsgSuccess = new ArrayList<>();
    //发送消息失败的好友列表
    private List<ClientFriends> sendMsgFail = new ArrayList<>();

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<Contact> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Contact> memberList) {
        this.memberList = memberList;
    }

    public List<ClientFriends> getSendMsgSuccess() {
        return sendMsgSuccess;
    }

    public void setSendMsgSuccess(List<ClientFriends> sendMsgSuccess) {
        this.sendMsgSuccess = sendMsgSuccess;
    }

    public List<ClientFriends> getSendMsgFail() {
        return sendMsgFail;
    }

    public void setSendMsgFail(List<ClientFriends> sendMsgFail) {
        this.sendMsgFail = sendMsgFail;
    }

    public List<Contact> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Contact> groupList) {
        this.groupList = groupList;
    }

    public List<Contact> getPublicList() {
        return publicList;
    }

    public void setPublicList(List<Contact> publicList) {
        this.publicList = publicList;
    }
}
