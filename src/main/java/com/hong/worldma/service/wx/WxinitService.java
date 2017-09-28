package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.entity.wx.ReqHeader;
import com.hong.worldma.entity.wx.WxInitResp;
import com.hong.worldma.util.wx.WxJs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
@Service
public class WxinitService {
    private static Logger logger = LoggerFactory.getLogger(WxinitService.class);

    @Autowired
    private OkHttpClient okClient;

    //处理webwxinit请求
    public void wxInit(ReqMethodParam param) {
        String url = new StringBuffer().append(param.getWxinitUrl()).append("?r=").append(WxJs.getLoginR()).append("&pass_ticket=").append(param.getP().getPass_ticket()).toString();
        logger.info("----------------------Wxinit--reqUrl = " + url);
        String cookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                .append(param.getCv().getLast_wxuin()).toString();
        logger.info("----------------------Wxinit--reqUrl--cookie = " + cookie);
        String reqBody = new StringBuffer().append("{\"BaseRequest\":{\"Uin\":\"").append(param.getP().getWxuin())
                .append("\",\"Sid\":\"").append(param.getP().getWxsid()).append("\",\"Skey\":\"").append(param.getP().getSkey())
                .append("\",\"DeviceID\":\"").append(WxJs.getDeviceID()).append("\"}}").toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqBody);
        logger.info("----------------------Wxinit--reqUrl--reqBody = " + reqBody);
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
                .post(body)
                .build();
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            result = response.body().string();
            logger.info("----------------------Wxinit--reqUrlResp = " + result);
            //解析WaitLogin的返回数据
            WxInitResp resp = JSON.parseObject(result, WxInitResp.class);
            param.setUserName(resp.getUser().getUserName());
            param.setUin(resp.getUser().getUin());
            param.setList(resp.getSyncKey().getList());
        } catch (IOException e) {
            logger.error("----------------------Wxinit--reqUrlERROE", e);
        }
    }
}
