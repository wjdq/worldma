package com.hong.worldma.service.wx;

import com.hong.worldma.dao.ActivityImgPathMapper;
import com.hong.worldma.dao.ActivityMsgMapper;
import com.hong.worldma.entity.wm.ActivityMsg;
import com.hong.worldma.entity.wx.ContactAndFriends;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.service.wm.HandleResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 处理 SendImgService 和 SendTextService所需要的数据，并调用它们的方法进行消息发送
 * @Author: hong
 * @Date: 2017-09-14
 */
@Service
public class SendMsgService {
    private static Logger logger = LoggerFactory.getLogger(SendMsgService.class);

    @Autowired
    private ActivityMsgMapper activityMsgMapper;
    @Autowired
    private ActivityImgPathMapper activityImgPathMapper;
    @Autowired
    private SendTextService sendTextService;
    @Autowired
    private SendImgService sendImgService;
    @Autowired
    private HandleResultService handleResultService;

    public void sendMsg(ReqMethodParam param, ContactAndFriends contactAndFriends){
        //获取待发送的活动消息
        List<ActivityMsg> activityMsgList = activityMsgMapper.getStatus_One();
        if (activityMsgList != null && activityMsgList.size() > 0) {
            //通过该标记判断发送文字消息和图片消息时是否需要同时向contactAndFriends的List集合中添加同一好友信息
            String f = "y";
            for (ActivityMsg msg : activityMsgList) {
                //Type = 1 文字消息
                if (msg.getType().equals("1")) {
                    String content = msg.getMsg(); //消息内容
                    sendTextService.sendText(param, contactAndFriends, content, f);
                    f = "n";
                } else {
                    //Type = 2 图片消息，获取图片的存放路径
                    String pathname = activityImgPathMapper.getImgPath(msg.getId());
                    sendImgService.sendImg(param, contactAndFriends, pathname, f);
                    f = "n";
                }
            }
        }
        //启动新的线程，持久化扫码结果
        WxThreadPool.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                handleResultService.HandleResult(contactAndFriends);
            }
        });
        logger.info("------------------contactAndFriends成功发送的数量 = " + contactAndFriends.getSendMsgSuccess());
        logger.info("------------------contactAndFriends好友数量 = " + contactAndFriends.getMemberList().size());
    }
}
