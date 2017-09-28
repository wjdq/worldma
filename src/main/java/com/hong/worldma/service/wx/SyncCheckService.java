package com.hong.worldma.service.wx;

import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.entity.wx.SyncKeyMap;
import com.hong.worldma.util.wx.WxJs;
import com.hong.worldma.entity.wx.ReqHeader;
import com.hong.worldma.util.wx.RegResolve;
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
 * @Date: 2017-09-10
 */
@Service
public class SyncCheckService {
    private static Logger logger = LoggerFactory.getLogger(SyncCheckService.class);

    @Autowired
    private OkHttpClient okClient;

    @Autowired
    private WxSyncService wxSyncService;

    public String syncCheck(ReqMethodParam param, String urlSyncCheck, String cookie){
        String reqURL;
        String reqCookie;
        if (urlSyncCheck.equals("first")) {
            //截取synccheck请求链接需要的参数
            List<SyncKeyMap> list = param.getList();
            String synccheck = "";
            int con = list.size();
            for (int i = 0; i < con; i++){
                if (i < con - 1) {
                    synccheck += list.get(i).Key + "_" + list.get(i).Val + "|";
                }else {
                    synccheck += list.get(i).Key + "_" + list.get(i).Val;
                }
            }
            reqURL = new StringBuffer().append(param.getSynccheckUrl()).append(new Date().getTime()).append("&skey=")
                    .append(param.getP().getSkey()).append("&sid=").append(param.getP().getWxsid()).append("&uin=")
                    .append(param.getP().getWxuin()).append("&deviceid=").append(WxJs.getDeviceID()).append("&synckey=")
                    .append(synccheck).append("&_=").append(new Date().getTime()).toString(); //synccheck
            logger.info("----------------------SyncCheck--reqUrl = " + reqURL);
        }else {
            reqURL = new StringBuffer().append(param.getSynccheckUrl()).append(new Date().getTime()).append("&skey=")
                    .append(param.getP().getSkey()).append("&sid=").append(param.getP().getWxsid()).append("&uin=")
                    .append(param.getP().getWxuin()).append("&deviceid=").append(WxJs.getDeviceID()).append("&synckey=")
                    .append(urlSyncCheck).append("&_=").append(new Date().getTime()).toString(); //urlSyncCheck
            logger.info("----------------------SyncCheck--reqUrl = " + reqURL);
        }
        if (cookie.equals("first")) {
            reqCookie = new StringBuffer().append("mm_lang=zh_CN; ").append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                    .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                    .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                    .toString();
            logger.info("----------------------SyncCheck--reqUrl--cookie = " + reqCookie);
        }else {
            reqCookie = new StringBuffer().append("mm_lang=zh_CN; ").append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                    .append("; ").append(param.getCv().getWxloadtime()).append("_expired; ").append(param.getCv().getWebwx_data_ticket())
                    .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                    .append("; ").append(param.getCv().getWxpluginkey()).toString();
            logger.info("----------------------SyncCheck--reqUrl--cookie = " + reqCookie);
        }
        Request request = new Request.Builder()
                .url(reqURL)
                .addHeader("Host", param.getSynccheckHost())
                .addHeader("Accept", ReqHeader.Accept[0])
                .addHeader("Accept-Language", ReqHeader.Accept_Language)
                .addHeader("Connection", ReqHeader.Connection)
                .addHeader("Referer", param.getReferer())
                .addHeader("user-Agent", ReqHeader.User_Agent)
                .addHeader("DNT", ReqHeader.DNT)
                .addHeader("Cookie", reqCookie)
                .build();
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            result = response.body().string();
            logger.info("----------------------SyncCheck--reqUrlResp = " + result);
            //解析返回数据 window.synccheck={retcode:"0",selector:"0"}，如果请求后不是第一次调用该方法，将执行下边的消息同步代码（selector ！= 0 时执行）
            if (!(RegResolve.getSelector(result).equals("0")) && cookie.equals("second")){
                List<SyncKeyMap> syncList = param.getSyncKey();
                String skm = "";
                int syncCon = syncList.size();
                for (int i = 0; i < syncCon; i++) {
                    if (i < syncCon - 1) {
                        skm += "{\"Key\":" + syncList.get(i).Key + ",\"Val\":" + syncList.get(i).Val + "},";
                    } else {
                        skm += "{\"Key\":" + syncList.get(i).Key + ",\"Val\":" + syncList.get(i).Val + "}";
                    }
                }
                wxSyncService.wxSync(param, "second", skm, syncCon);
            }
            return RegResolve.getRetcode(result);

        } catch (IOException e) {
            logger.error("----------------------SyncCheck--reqUrlERROE", e);
        }
        return null;
    }
}
