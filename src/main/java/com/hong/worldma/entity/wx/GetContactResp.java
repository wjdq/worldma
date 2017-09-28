package com.hong.worldma.entity.wx;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
public class GetContactResp {
    public BaseResponse BaseResponse;
    public int MemberCount;
    public List<Contact> MemberList;
    public int Seq;

    public com.hong.worldma.entity.wx.BaseResponse getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(com.hong.worldma.entity.wx.BaseResponse baseResponse) {
        BaseResponse = baseResponse;
    }

    public int getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(int memberCount) {
        MemberCount = memberCount;
    }

    public List<Contact> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<Contact> memberList) {
        MemberList = memberList;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }
}
