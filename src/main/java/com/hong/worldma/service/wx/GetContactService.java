package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.entity.wx.GetContactResp;
import com.hong.worldma.entity.wx.ReqHeader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-10
 */
@Service
public class GetContactService {
    private static Logger logger = LoggerFactory.getLogger(GetContactService.class);

    @Autowired
    private OkHttpClient okClient;
    @Autowired
    private SyncCheckService syncCheckService;
    @Autowired
    private WxSyncService wxSyncService;
    @Autowired
    private FilterContactService filterClientService;

    public void getContact(ReqMethodParam param){
        String url = new StringBuffer().append(param.getContactUrl()).append("?pass_ticket=").append(param.getP().getPass_ticket())
                .append("&r=").append(new Date().getTime()).append("&seq=0&skey=").append(param.getP().getSkey()).toString();
        logger.info("----------------------GetContact--reqUrl = " + url);
        String cookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                .append(param.getCv().getLast_wxuin()).toString();
        logger.info("----------------------GetContact--reqUrl--cookie = " + cookie);
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
            logger.info("----------------------GetContact--reqUrlResp = " + result);
            GetContactResp getContactResp = JSON.parseObject(result, GetContactResp.class);
            param.setContactResp(getContactResp);
            //执行synccheck请求--------------------------
            String retcode = syncCheckService.syncCheck(param, "first", "first");
            //如果第一次心跳检查成功，继续后续请求任务,否则结束请求并推送“无法连接微信服务器进行消息群发”
            if (retcode.equals("0")) {
                wxSyncService.wxSync(param, "first", "first", 0);
                filterClientService.startSendMsg(param);
            }else {
                //推送“无法连接微信服务器进行消息群发”

                logger.error("----------------------GetContact--第一次心跳检查失败，无法连接微信服务器进行消息群发");
                return;
            }
        } catch (IOException e) {
            logger.error("----------------------GetContact--reqUrlERROE", e);
        }
    }
}
