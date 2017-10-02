package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.ReqHeader;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.entity.wx.SyncKeyMap;
import com.hong.worldma.entity.wx.WxSyncResp;
import com.hong.worldma.util.wx.WxJs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
@Service
public class WxSyncService {

    private static Logger logger = LoggerFactory.getLogger(WxSyncService.class);

    @Autowired
    private OkHttpClient okClient;

    public void wxSync(ReqMethodParam param, String cookie, String skmBody, int skmCount){
        String reqCookie;
        String reqBody;
        String url = new StringBuffer().append(param.getWebwxsyncUrl()).append("?sid=").append(param.getP().getWxsid()).append("&skey=")
                .append(param.getP().getSkey()).append("&pass_ticket=").append(param.getP().getPass_ticket()).toString();
        logger.info("----------------------WxSync--reqUrl = " + url);
        if (cookie.equals("first")) {
            reqCookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                    .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                    .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                    .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                    .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                    .append(param.getCv().getLast_wxuin()).toString();
            logger.info("----------------------WxSync--reqUrl--cookie = " + reqCookie);
        }else {
            reqCookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                    .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                    .append("; ").append(param.getCv().getWxloadtime()).append("_expired; ").append(param.getCv().getWebwx_data_ticket())
                    .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                    .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                    .append(param.getCv().getLast_wxuin()).append("; ").append(param.getCv().getWxpluginkey()).toString();
            logger.info("----------------------WxSync--reqUrl--cookie = " + reqCookie);
        }
        if (skmBody.equals("first")) {
            List<SyncKeyMap> list = param.getList();
            String skm = "";
            int con = list.size();
            for (int i = 0; i < con; i++) {
                if (i < con-1) {
                    skm += "{\"Key\":" + list.get(i).Key + ",\"Val\":" + list.get(i).Val + "},";
                }else {
                    skm += "{\"Key\":" + list.get(i).Key + ",\"Val\":" + list.get(i).Val + "}";
                }
            }
            reqBody = new StringBuffer().append("{\"BaseRequest\":{\"Uin\":\"").append(param.getP().getWxuin())
                    .append("\",\"Sid\":\"").append(param.getP().getWxsid()).append("\",\"Skey\":\"").append(param.getP().getSkey())
                    .append("\",\"DeviceID\":\"").append(WxJs.getDeviceID()).append("\"},").append("\"SyncKey\":{\"Count\":")
                    .append(con).append(",\"List\":[").append(skm).append("]}}").toString();
            logger.info("----------------------WxSync--reqUrl--reqBody = " + reqBody);
        }else {
            reqBody = new StringBuffer().append("{\"BaseRequest\":{\"Uin\":\"").append(param.getP().getWxuin())
                    .append("\",\"Sid\":\"").append(param.getP().getWxsid()).append("\",\"Skey\":\"").append(param.getP().getSkey())
                    .append("\",\"DeviceID\":\"").append(WxJs.getDeviceID()).append("\"},").append("\"SyncKey\":{\"Count\":")
                    .append(skmCount).append(",\"List\":[").append(skmBody).append("]}}").toString();
            logger.info("----------------------WxSync--reqUrl--reqBody = " + reqBody);
        }
        RequestBody reqBody_2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host", param.getHost())
                .addHeader("Accept", ReqHeader.Accept[1])
                .addHeader("Accept-Language", ReqHeader.Accept_Language)
                .addHeader("Connection", ReqHeader.Connection)
                .addHeader("Referer", param.getReferer())
                .addHeader("user-Agent", ReqHeader.User_Agent)
                .addHeader("DNT", ReqHeader.DNT)
                .addHeader("Cookie", reqCookie)
                .post(reqBody_2)
                .build();
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            result = response.body().string();
            logger.info("----------------------WxSync--reqUrlResp = " + result);
            //把WxSync的响应信息转化成WxSyncResp对象
            WxSyncResp wxSyncResp = JSON.parseObject(result, WxSyncResp.class);
            param.setSyncCheckKey(wxSyncResp.getSyncCheckKey().getList());
            param.setSyncKey(wxSyncResp.getSyncKey().getList());
            if (skmBody.equals("first")) {
                //获取并解析响应头中的Set-Cookie信息
                List<String> cookieList = response.headers("Set-Cookie");
                String wxPluginKey = cookieList.get(1).split(";")[0];
                param.getCv().setWxpluginkey(wxPluginKey);
            }
        } catch (IOException e) {
            logger.error("----------------------WxSync--reqUrlERROE", e);
        }
    }
}
