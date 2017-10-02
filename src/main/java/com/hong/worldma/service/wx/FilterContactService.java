package com.hong.worldma.service.wx;


import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.entity.wx.SyncKeyMap;
import com.hong.worldma.entity.wx.ContactAndFriends;
import com.hong.worldma.entity.wx.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 过滤好友列表
 * @Author: hong
 * @Date: 2017-09-07
 */
@Service
public class FilterContactService {

    @Autowired
    private SyncCheckService syncCheckService;
    @Autowired
    private SendMsgService sendMsgService;

    private ContactAndFriends getFilterContactList(ReqMethodParam param) {
        //获取从GetContactService响应中得到的好友列表数据
        List<Contact> memberList = param.getContactResp().getMemberList();
        //保存好友筛选数据的ContactAndFriends类
        ContactAndFriends contactAndFriends = new ContactAndFriends();
        Iterator<Contact> iterator = memberList.iterator();
        //筛选
        while (iterator.hasNext()) {
            Contact c = iterator.next();
            if (c.getUserName().equals(param.getUserName())) {
                //把扫码人的信息添加到 ContactAndFriends中
                Client client = new Client();
                client.setCity(c.getProvince() + "-" + c.getCity());
                client.setNick_name(c.getNickName());
                client.setSex(c.getSex());
                client.setUin(param.getUin());
                contactAndFriends.setClient(client);
                continue;
            }
            //过滤掉以下好友
            //c.getUserName().equals("filehelper") || c.getUserName().equals("weixin") ||
            if (c.getNickName().equals("QQ安全中心") || c.getUserName().equals(param.getUserName())) {
                iterator.remove();
            }
        }
        //将好友的总数量存放到 ContactAndFriends
        contactAndFriends.setMemberCount(param.getContactResp().getMemberCount());
        //将过滤后的好友列表存放到ContactAndFriends
        contactAndFriends.setMemberList(memberList);
        param.setContactResp(null);
        return contactAndFriends;
    }

    public void startSendMsg(ReqMethodParam param){
        ContactAndFriends contactAndFriends = getFilterContactList(param);
        //启动心跳检查、消息监听线程
        String status = "" + new Date().getTime();
        WxHashMap.retcodeMap.put(status, "ok");
        WxThreadPool.cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (WxHashMap.retcodeMap.get(status).equals("ok")) {
                    //构建syncCheck  URL中需要的请求参数
                    List<SyncKeyMap> list = param.getSyncCheckKey();
                    String synccheck = "";
                    int con = list.size();
                    for (int i = 0; i < con; i++) {
                        if (i < con - 1) {
                            synccheck += list.get(i).Key + "_" + list.get(i).Val + "|";
                        } else {
                            synccheck += list.get(i).Key + "_" + list.get(i).Val;
                        }
                    }
                    String retcode = syncCheckService.syncCheck(param, synccheck, "second");
                    if (!retcode.equals("0")) WxHashMap.retcodeMap.put(status, "end");
                }
                WxHashMap.retcodeMap.remove(status);
            }
        });
        param.setStatus(status);
        //添加消息推送“正在发送消息，请稍等...”

        //调用发送消息请求---------------------------
        sendMsgService.sendMsg(param, contactAndFriends);
        //群发消息结束
        WxHashMap.retcodeMap.put(status, "end");

        //添加推送消息“发送完成，请刷新获取新二维码...”

    }
}
