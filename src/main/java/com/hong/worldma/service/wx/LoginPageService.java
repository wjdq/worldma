package com.hong.worldma.service.wx;

import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.util.wx.RegResolve;
import com.hong.worldma.util.wx.ResolveSetCookie;
import com.hong.worldma.entity.wx.CookieValues;
import com.hong.worldma.entity.wx.LoginPageResp;
import com.hong.worldma.entity.wx.ReqHeader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
@Service
public class LoginPageService {
    private static Logger logger = LoggerFactory.getLogger(LoginPageService.class);
    @Autowired
    private OkHttpClient okClient;
    @Autowired
    private WxinitService wxinitService;
    @Autowired
    private WxStatusNotifyService wxStatusNotifyService;

    //处理loginPage请求
    public String loginPage(ReqMethodParam param) {
        String url = new StringBuffer().append(param.getLoginPage()).append("&fun=new&version=v2").toString();
        logger.info("----------------------LoginPageResp--reqUrl = " + url);
        String cookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1").toString();
        logger.info("----------------------LoginPageResp--reqUrl--cookie = " + cookie);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host", param.getHost())
                .addHeader("Accept", ReqHeader.Accept[1])
                .addHeader("Accept-Language", ReqHeader.Accept_Language)
                .addHeader("Connection", ReqHeader.Connection)
                .addHeader("Referer", param.getReferer())
                .addHeader("user-Agent", ReqHeader.User_Agent)
                .addHeader("DNT", ReqHeader.DNT)
                .addHeader("Cookie", cookie)
                .build();
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            result = response.body().string();
            logger.info("----------------------LoginPageResp--reqUrlResp = " + result);
            //获取XML响应解析后的数据
            LoginPageResp loginPageResp = RegResolve.lpResp(result);
            logger.info("----------------------LoginPageResp--XMLRespData = " + loginPageResp.toString());
            param.setP(loginPageResp);
            if (loginPageResp.getRet().equals("0")) {
                if (loginPageResp.getWxsid().indexOf("+") >= 0 || loginPageResp.getWxsid().indexOf("/") >= 0){
                    return "ERROR";
                }
                //获取并解析响应头中的Set-Cookie信息
                List<String> cookieList = response.headers("Set-Cookie");
                CookieValues cv = new ResolveSetCookie().getCookie(cookieList);
                logger.info("----------------------LoginPageResp--CookieRespData = " + cv.toString());
                param.setCv(cv);
                //微信初始化请求------------------
                wxinitService.wxInit(param);
                //执行WxStatusNotifyService请求--------------------------
                wxStatusNotifyService.wxStatusNotify(param);
                return "success";
            } else {
                logger.error("-------------------LoginPage登录失败！---------" + loginPageResp.getMessage());
                //推送无法登陆的消息  ----  loginPageResp.getMessage()
                return "LoginERROR";
            }

        } catch (IOException e) {
            logger.error("----------------------LoginPageResp--reqUrlERROR", e);
            return "ReqERROR";
        }

    }
}
