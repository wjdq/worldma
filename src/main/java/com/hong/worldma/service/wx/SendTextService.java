package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.*;
import com.hong.worldma.util.wx.WxJs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-11
 */
@Service
public class SendTextService {

    private static Logger logger = LoggerFactory.getLogger(SendTextService.class);

    @Autowired
    private OkHttpClient okClient;


    public void sendText(ReqMethodParam param, ContactAndFriends contactAndFriends, String content, String f) {
        String url = new StringBuffer().append(param.getSendTextUrl()).append("?lang=zh_CN&pass_ticket=").append(param.getP().getPass_ticket()).toString();
        logger.info("----------------------sendText--reqUrl = " + url);
        String cookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                .append(param.getCv().getLast_wxuin()).append("; ").append(param.getCv().getWxpluginkey()).toString();
        logger.info("----------------------sendText--reqUrl--cookie = " + cookie);
        for (Contact c : contactAndFriends.getMemberList()) {
            //发消息前判断心跳检测是否正常
            if (WxHashMap.retcodeMap.get(param.getStatus()).equals("ok")) {
                //请求参数 消息体LocalID和ClientMsgId的值(时间戳+ 4 位随机值)
                String lcID = "" + new Date().getTime() + (new Random().nextInt(9000) + 1000);
                //------------------------------------------------------------上线注掉
//                if (c.getUserName().equals("filehelper") || c.getUserName().equals("weixin")) {
                String reqBody = new StringBuffer().append("{\"BaseRequest\":{\"Uin\":\"").append(param.getP().getWxuin())
                        .append("\",\"Sid\":\"").append(param.getP().getWxsid()).append("\",\"Skey\":\"").append(param.getP().getSkey())
                        .append("\",\"DeviceID\":\"").append(WxJs.getDeviceID()).append("\"},").append("\"Msg\":{\"Type\":1,\"Content\":\"")
                        .append(content).append("\",\"FromUserName\":\"").append(param.getUserName()).append("\",\"ToUserName\":\"")
                        .append(c.getUserName()).append("\",\"LocalID\":").append(lcID).append(",\"ClientMsgId\":").append(lcID).append("},\"Scene\":0}")
                        .toString();
                logger.info("----------------------sendText--reqUrl--reqBody = " + reqBody);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqBody);
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
                    logger.info("----------------------sendText--reqUrlResp = " + result);
                    //解析SendText的返回数据
                    SendMsgResp sendMsgResp = JSON.parseObject(result, SendMsgResp.class);
                    if (sendMsgResp.getBaseResponse().getRet().equals("0") && f.equals("y")) {
                        contactAndFriends.setSendMsgSuccess(contactAndFriends.getSendMsgSuccess() + 1);
                    } else if (!sendMsgResp.getBaseResponse().getRet().equals("0") && f.equals("y")) {
                        contactAndFriends.setSendMsgFail(contactAndFriends.getSendMsgFail() + 1);
                    }
                } catch (IOException e) {
                    logger.error("----------------------sendText--reqUrlERROE", e);
                    return;
                }
//                }
                //当前线程睡眠2.3秒在进行群发
                try {
                    Thread.sleep(2300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                ///推送“扫码者已退出登录，或扫码者与微信服务器链接不正常，无法继续群发消息”信息

                logger.error("----------------------sendText------扫码者已退出登录，或扫码者与微信服务器链接不正常，无法继续群发消息");
                return;
            }
        }

    }
}
