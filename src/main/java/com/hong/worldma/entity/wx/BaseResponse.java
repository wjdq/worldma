package com.hong.worldma.entity.wx;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
public class BaseResponse {
    public String Ret;
    public String ErrMsg;

    public String getRet() {
        return Ret;
    }

    public void setRet(String ret) {
        Ret = ret;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
