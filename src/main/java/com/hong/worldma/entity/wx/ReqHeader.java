package com.hong.worldma.entity.wx;

/**
 * @Description: 请求头信息
 * @Author: hong
 * @Date: 2017-08-27
 */
public class ReqHeader {
    public static String[] Host ={"login.wx.qq.com", "wx.qq.com", "file.wx.qq.com","login.wx2.qq.com", "wx2.qq.com", "file.wx2.qq.com", "webpush.wx.qq.com", "webpush.wx2.qq.com"};
    public static String Accept_Language = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";
    public static String[] Accept = {"*/*", "application/json, text/plain, */*"};
    public static String Connection = "Keep-Alive";
    public static String DNT = "1";
    public static String[] Referer = {"https://wx.qq.com/", "https://wx2.qq.com/"};
    public static String User_Agent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0";
}
