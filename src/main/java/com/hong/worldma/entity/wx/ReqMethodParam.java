package com.hong.worldma.entity.wx;

import java.util.List;

/**
 * @Description:  请求微信接口方法的参数
 * @Author: hong
 * @Date: 2017-09-08
 */
public class ReqMethodParam {
//    private String pvi;
//    private String si;
    private LoginPageResp p;
    private CookieValues cv;
    private GetContactResp contactResp;
    private String host;
    private String referer;
    private String synccheckHost;
    private String loginPage;
    private String wxinitUrl;
    private String userName;
    private String uin;
    private String contactUrl;
    private String  loadmediaUrl;
    private String loadmediaHost;
    private String wxstatusnotifyUrl;
    private String synccheckUrl;
    private String webwxsyncUrl;
    private List<SyncKeyMap> list; //存放从wxInit响应中获取的SyncKey
    private String sendTextUrl;
    private List<SyncKeyMap> SyncKey;
    private List<SyncKeyMap> SyncCheckKey;
    private String sendImgUrl;
    //存放心跳状态的WxHashMap键
    private String status;

//    public String getPvi() {
//        return pvi;
//    }
//
//    public void setPvi(String pvi) {
//        this.pvi = pvi;
//    }
//
//    public String getSi() {
//        return si;
//    }
//
//    public void setSi(String si) {
//        this.si = si;
//    }

    public LoginPageResp getP() {
        return p;
    }

    public void setP(LoginPageResp p) {
        this.p = p;
    }

    public CookieValues getCv() {
        return cv;
    }

    public void setCv(CookieValues cv) {
        this.cv = cv;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getWxinitUrl() {
        return wxinitUrl;
    }

    public GetContactResp getContactResp() {
        return contactResp;
    }

    public void setContactResp(GetContactResp contactResp) {
        this.contactResp = contactResp;
    }

    public void setWxinitUrl(String wxinitUrl) {
        this.wxinitUrl = wxinitUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getLoadmediaUrl() {
        return loadmediaUrl;
    }

    public void setLoadmediaUrl(String loadmediaUrl) {
        this.loadmediaUrl = loadmediaUrl;
    }

    public String getLoadmediaHost() {
        return loadmediaHost;
    }

    public void setLoadmediaHost(String loadmediaHost) {
        this.loadmediaHost = loadmediaHost;
    }

    public String getWxstatusnotifyUrl() {
        return wxstatusnotifyUrl;
    }

    public void setWxstatusnotifyUrl(String wxstatusnotifyUrl) {
        this.wxstatusnotifyUrl = wxstatusnotifyUrl;
    }

    public String getSynccheckHost() {
        return synccheckHost;
    }

    public void setSynccheckHost(String synccheckHost) {
        this.synccheckHost = synccheckHost;
    }

    public String getSynccheckUrl() {
        return synccheckUrl;
    }

    public void setSynccheckUrl(String synccheckUrl) {
        this.synccheckUrl = synccheckUrl;
    }

    public String getWebwxsyncUrl() {
        return webwxsyncUrl;
    }

    public void setWebwxsyncUrl(String webwxsyncUrl) {
        this.webwxsyncUrl = webwxsyncUrl;
    }

    public List<SyncKeyMap> getList() {
        return list;
    }

    public void setList(List<SyncKeyMap> list) {
        this.list = list;
    }

    public String getSendTextUrl() {
        return sendTextUrl;
    }

    public void setSendTextUrl(String sendTextUrl) {
        this.sendTextUrl = sendTextUrl;
    }

    public String getSendImgUrl() {
        return sendImgUrl;
    }

    public void setSendImgUrl(String sendImgUrl) {
        this.sendImgUrl = sendImgUrl;
    }

    public List<SyncKeyMap> getSyncKey() {
        return SyncKey;
    }

    public void setSyncKey(List<SyncKeyMap> syncKey) {
        SyncKey = syncKey;
    }

    public List<SyncKeyMap> getSyncCheckKey() {
        return SyncCheckKey;
    }

    public void setSyncCheckKey(List<SyncKeyMap> syncCheckKey) {
        SyncCheckKey = syncCheckKey;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
