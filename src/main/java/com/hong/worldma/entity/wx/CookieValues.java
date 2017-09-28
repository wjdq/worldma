package com.hong.worldma.entity.wx;

/**
 * @Description:  //服务器返回的Set-Cookie信息
 * @Author: hong
 * @Date: 2017-09-09
 */
public class CookieValues {

    private String wxuin;
    private String wxsid;
    private String wxloadtime;
    private String webwx_data_ticket;
    private String webwxuvid;
    private String webwx_auth_ticket;
    private String login_frequency = "1";
    private String last_wxuin;
    private String wxpluginkey;

    public String getWxuin() {
        return wxuin;
    }

    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    public String getWxsid() {
        return wxsid;
    }

    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    public String getLast_wxuin() {
        return last_wxuin;
    }

    public void setLast_wxuin(String last_wxuin) {
        this.last_wxuin = last_wxuin;
    }

    public String getWxloadtime() {
        return wxloadtime;
    }

    public void setWxloadtime(String wxloadtime) {
        this.wxloadtime = wxloadtime;
    }

    public String getWebwx_data_ticket() {
        return webwx_data_ticket;
    }

    public void setWebwx_data_ticket(String webwx_data_ticket) {
        this.webwx_data_ticket = webwx_data_ticket;
    }

    public String getWebwxuvid() {
        return webwxuvid;
    }

    public void setWebwxuvid(String webwxuvid) {
        this.webwxuvid = webwxuvid;
    }

    public String getWebwx_auth_ticket() {
        return webwx_auth_ticket;
    }

    public void setWebwx_auth_ticket(String webwx_auth_ticket) {
        this.webwx_auth_ticket = webwx_auth_ticket;
    }

    public String getLogin_frequency() {
        return login_frequency;
    }

    public void setLogin_frequency(String login_frequency) {
        this.login_frequency = login_frequency;
    }

    public String getWxpluginkey() {
        return wxpluginkey;
    }

    public void setWxpluginkey(String wxpluginkey) {
        this.wxpluginkey = wxpluginkey;
    }

    @Override
    public String toString() {
        return "CookieValues{" +
                "wxuin='" + wxuin + '\'' +
                ", wxsid='" + wxsid + '\'' +
                ", wxloadtime='" + wxloadtime + '\'' +
                ", webwx_data_ticket='" + webwx_data_ticket + '\'' +
                ", webwxuvid='" + webwxuvid + '\'' +
                ", webwx_auth_ticket='" + webwx_auth_ticket + '\'' +
                ", login_frequency='" + login_frequency + '\'' +
                ", last_wxuin='" + last_wxuin + '\'' +
                ", wxpluginkey='" + wxpluginkey + '\'' +
                '}';
    }
}
