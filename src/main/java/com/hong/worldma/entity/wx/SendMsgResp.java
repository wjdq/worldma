package com.hong.worldma.entity.wx;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-14
 */
public class SendMsgResp {
    public BaseResponse BaseResponse;
    public String MsgID;
    public String LocalID;

    public com.hong.worldma.entity.wx.BaseResponse getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(com.hong.worldma.entity.wx.BaseResponse baseResponse) {
        BaseResponse = baseResponse;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getLocalID() {
        return LocalID;
    }

    public void setLocalID(String localID) {
        LocalID = localID;
    }
}
