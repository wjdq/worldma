package com.hong.worldma.service.wx;

import com.hong.worldma.entity.wx.*;
import com.hong.worldma.util.wx.RegResolve;
import com.hong.worldma.util.wx.ResolveSetCookie;
import com.hong.worldma.util.wx.WxJs;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
@Service
public class WaitLoginService {
    private static Logger logger = LoggerFactory.getLogger(WaitLoginService.class);
    @Autowired
    private OkHttpClient okClient;
    @Autowired
    private LoginPageService loginPageService;


    public void waitLoginReq(String uuid){
        //构建请求方法参数对象
        ReqMethodParam param = new ReqMethodParam();
        //请求地址
        String reqUrl = new StringBuffer().append(ReqUrl.login)
                .append("?loginicon=true&uuid=")
                .append(uuid).append("&tip=0&r=").append(WxJs.getLoginR())
                .append("&_=").append(new Date().getTime()).toString();
        logger.info("----------------------WaitLogin--reqUrl = " + reqUrl);
        String cookie = new StringBuffer().append("mm_lang=zh_CN").toString();
        logger.info("----------------------WaitLogin--reqUrl--cookie = " + cookie);
        Request request = new Request.Builder()
                .url(reqUrl)
                .addHeader("Host", ReqHeader.Host[0])
                .addHeader("Accept", ReqHeader.Accept[0])
                .addHeader("Accept-Language", ReqHeader.Accept_Language)
                .addHeader("Connection", ReqHeader.Connection)
                .addHeader("Referer", ReqHeader.Referer[0])
                .addHeader("user-Agent", ReqHeader.User_Agent)
                .addHeader("DNT", ReqHeader.DNT)
                .addHeader("Cookie", cookie)
                .build();
        System.out.println("线程的名字是:" + Thread.currentThread().getName() + " 开始执行任务");
        while (true){
            Response response = null;
            String result = null;
            try {
                response = okClient.newCall(request).execute();
                result = response.body().string();
                logger.info("----------------------WaitLogin--reqUrlResp = " + result);
            } catch (IOException e) {
                logger.error("----------------------WaitLogin--reqUrlERROE", e);
            }
            String erroe4ORsuccess2 = RegResolve.result2OR4(result);
            logger.info("----------------------解析WaitLogin--reqUrl响应erroe4ORsuccess2 = " + erroe4ORsuccess2);
            if (erroe4ORsuccess2.equals("200")){
                //截取 result 返回字符串中的链接地址
                String loginPage = result.split("\"")[1];
                logger.info("----------------------截取WaitLogin--reqUrl-200-响应中的链接地址 = " + loginPage);
                if (RegResolve.wxORwx2(loginPage).equals("wx.qq.com")){
                    param.setLoginPage(loginPage);
                    param.setHost(ReqHeader.Host[1]);
                    param.setReferer(ReqHeader.Referer[0]);
                    param.setWxinitUrl(ReqUrl.wxinit);
                    param.setWxstatusnotifyUrl(ReqUrl.wxstatusnotify);
                    param.setContactUrl(ReqUrl.getcontact);
                    param.setSynccheckUrl(ReqUrl.synccheck);
                    param.setSynccheckHost(ReqHeader.Host[6]);
                    param.setWebwxsyncUrl(ReqUrl.webwxsync);
                    param.setSendTextUrl(ReqUrl.sendText);
                    param.setLoadmediaUrl(ReqUrl.loadmedia);
                    param.setLoadmediaHost(ReqHeader.Host[2]);
                    param.setSendImgUrl(ReqUrl.sendImg);
                    //处理wx------loginPage请求-----------------
                    logger.info("----------------wx账号------------");
                    while (true) {
                        String status = loginPageService.loginPage(param);
                        if (!status.equals("ERROR")){
                            break;
                        }
                    }
                }else {
                    param.setLoginPage(loginPage);
                    param.setHost(ReqHeader.Host[4]);
                    param.setReferer(ReqHeader.Referer[1]);
                    param.setWxinitUrl(ReqUrl.wxinit2);
                    param.setWxstatusnotifyUrl(ReqUrl.wxstatusnotify2);
                    param.setContactUrl(ReqUrl.getcontact2);
                    param.setSynccheckUrl(ReqUrl.synccheck2);
                    param.setSynccheckHost(ReqHeader.Host[7]);
                    param.setWebwxsyncUrl(ReqUrl.webwxsync2);
                    param.setSendTextUrl(ReqUrl.sendText2);
                    param.setLoadmediaUrl(ReqUrl.loadmedia2);
                    param.setLoadmediaHost(ReqHeader.Host[5]);
                    param.setSendImgUrl(ReqUrl.sendImg2);
                    //处理wx2------loginPage请求-----------------
                    logger.info("----------------wx2账号------------");
                    while (true) {
                        String status = loginPageService.loginPage(param);
                        if (!status.equals("ERROR")){
                            break;
                        }
                    }
                }
                break;
            }else if (erroe4ORsuccess2.equals("400")){
                break;
            }

        }
        System.out.println("------------------结束");
    }
}
