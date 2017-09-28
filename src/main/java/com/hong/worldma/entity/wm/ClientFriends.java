package com.hong.worldma.entity.wm;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-03
 */
public class ClientFriends extends Client{
    private String remarkName;
    private Integer clientID;
    private String userName;

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
