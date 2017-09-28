package com.hong.worldma.util.wx;

import com.hong.worldma.entity.wx.LoginPageResp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
public class RegResolve {
    //"<wxuin>(\\S+)</wxuin>"

    //解析SyncCheckService请求的返回数据，获取selector
    public static String getSelector(String str){
        return match("selector:\"(\\S+)\"}", str);
    }

    //解析SyncCheckService请求的返回数据，获取retcode
    public static String getRetcode(String str){
        return match("retcode:\"(\\S+)\",se", str);
    }
    public static LoginPageResp lpResp(String str) {
        LoginPageResp l = new LoginPageResp();
        l.setRet(match("<ret>(\\S+)</ret>", str));
        l.setMessage(match("<message>(\\S+)</message>", str));
        l.setSkey(match("<skey>(\\S+)</skey>", str));
        l.setWxsid(match("<wxsid>(\\S+)</wxsid>", str));
        l.setWxuin(match("<wxuin>(\\S+)</wxuin>", str));
        l.setPass_ticket(match("<pass_ticket>(\\S+)</pass_ticket>", str));
        l.setIsgrayscale(match("<isgrayscale>(\\S+)</isgrayscale>", str));
        return l;
    }
    //code=200;
    public static String result2OR4(String str){
        return match("code=(\\S+);", str);
    }
    //解析mmwebwx-bin/login响应，查看是wx2.qq.com或者是wx.qq.com
    public static String wxORwx2(String str) {
        return match("https://(\\S+)/cgi-bin", str);
    }

    private static String match(String p, String str) {
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
}
