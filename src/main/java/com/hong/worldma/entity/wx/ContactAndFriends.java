package com.hong.worldma.entity.wx;


import com.hong.worldma.entity.wm.Client;

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
    //发送消息成功的好友数
    private Integer sendMsgSuccess = 0;
    //发送消息失败的好友数
    private Integer sendMsgFail = 0;

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

    public Integer getSendMsgSuccess() {
        return sendMsgSuccess;
    }

    public void setSendMsgSuccess(Integer sendMsgSuccess) {
        this.sendMsgSuccess = sendMsgSuccess;
    }

    public Integer getSendMsgFail() {
        return sendMsgFail;
    }

    public void setSendMsgFail(Integer sendMsgFail) {
        this.sendMsgFail = sendMsgFail;
    }
}
