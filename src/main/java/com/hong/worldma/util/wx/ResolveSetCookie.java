package com.hong.worldma.util.wx;

import com.hong.worldma.entity.wx.CookieValues;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
public class ResolveSetCookie {

    public CookieValues getCookie(List<String> cookieList){
        CookieValues cv = new CookieValues();
        cv.setWxuin(cookieList.get(0).split(";")[0]);
        cv.setWxsid(cookieList.get(1).split(";")[0]);
        cv.setWxloadtime(cookieList.get(2).split(";")[0]);
        cv.setWebwx_data_ticket(cookieList.get(4).split(";")[0]);
        cv.setWebwxuvid(cookieList.get(5).split(";")[0]);
        cv.setWebwx_auth_ticket(cookieList.get(6).split(";")[0]);
        cv.setLogin_frequency("1");
        cv.setLast_wxuin(cookieList.get(0).split(";")[0].split("=")[1]);
        return cv;
    }
}
