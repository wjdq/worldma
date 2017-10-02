package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.*;
import com.hong.worldma.util.wx.WxJs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-12
 */
@Service
public class SendImgService {

    private static Logger logger = LoggerFactory.getLogger(SendImgService.class);

    @Autowired
    private OkHttpClient okClient;

    @Autowired
    private WxUploadImgService wxUploadImgService;

    public void sendImg(ReqMethodParam param, ContactAndFriends contactAndFriends, String pathname, String f) {
        //将图片上传到微信文件服务器，并获取mediaId返回值
        String mediaId = wxUploadImgService.upLoadImg(param, pathname);
        if (StringUtils.hasText(mediaId)) {
            String url = new StringBuffer().append(param.getSendImgUrl()).append("?fun=async&f=json&lang=zh_CN&pass_ticket=").append(param.getP().getPass_ticket()).toString();
            logger.info("----------------------SendImg--reqUrl = " + url);
            String cookie = new StringBuffer().append("mm_lang=zh_CN; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; ")
                    .append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                    .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                    .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                    .append("; login_frequency=").append(param.getCv().getLogin_frequency()).append("; last_wxuin=")
                    .append(param.getCv().getLast_wxuin()).append("; ").append(param.getCv().getWxpluginkey()).toString();
            logger.info("----------------------SendImg--reqUrl--cookie = " + cookie);
            for (Contact c : contactAndFriends.getMemberList()) {
                if (WxHashMap.retcodeMap.get(param.getStatus()).equals("ok")) {
                    //请求参数 消息体LocalID和ClientMsgId的值(时间戳+ 4 位随机值)
                    String lcID = "" + new Date().getTime() + (new Random().nextInt(9000) + 1000);
                    //------------------------------------------------------------上线注掉
//                    if (c.getUserName().equals("filehelper") || c.getUserName().equals("weixin")) {
                    String reqBody = new StringBuffer().append("{\"BaseRequest\":{\"Uin\":\"").append(param.getP().getWxuin())
                            .append("\",\"Sid\":\"").append(param.getP().getWxsid()).append("\",\"Skey\":\"").append(param.getP().getSkey())
                            .append("\",\"DeviceID\":\"").append(WxJs.getDeviceID()).append("\"},").append("\"Msg\":{\"Type\":3,\"MediaId\":\"")
                            .append(mediaId).append("\",\"Content\":\"\",").append("\"FromUserName\":\"").append(param.getUserName()).append("\",\"ToUserName\":\"")
                            .append(c.getUserName()).append("\",\"LocalID\":").append(lcID).append(",\"ClientMsgId\":").append(lcID).append("},\"Scene\":0}")
                            .toString();
                    logger.info("----------------------SendImg--reqUrl--reqBody = " + reqBody);
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
                        logger.info("----------------------SendImg--reqUrlResp = " + result);
                        //解析SendImg的返回数据
                        SendMsgResp sendMsgResp = JSON.parseObject(result, SendMsgResp.class);
                        if (sendMsgResp.getBaseResponse().getRet().equals("0") && f.equals("y")) {
                            contactAndFriends.setSendMsgSuccess(contactAndFriends.getSendMsgSuccess() + 1);
                        } else if (!sendMsgResp.getBaseResponse().getRet().equals("0") && f.equals("y")) {
                            contactAndFriends.setSendMsgFail(contactAndFriends.getSendMsgFail() + 1);
                        }
                    } catch (IOException e) {
                        logger.error("----------------------SendImg--reqUrlERROE", e);
                    }
//                    }
                    //当前线程睡眠3秒在进行群发
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    ///推送“扫码者已退出登录，或扫码者与微信服务器链接不正常，无法继续群发消息”信息

                    logger.error("----------------------sendText------扫码者已退出登录，或扫码者与微信服务器链接不正常，无法继续群发消息");
                    break;
                }
            }
        } else {
            //推送“mediaId为空无法发送图片消息”信息

            logger.error("----------------------SendImg获取图片上传的mediaId为空，无法发送图片消息------------------------");
            return;
        }
    }
}
