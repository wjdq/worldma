package com.hong.worldma.service.wm;

import com.hong.worldma.dao.ClientMapper;
import com.hong.worldma.entity.wm.ClientCount;
import com.hong.worldma.entity.wx.ContactAndFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:   处理扫码结束后的结果信息（ContactAndFriends类中包含的信息），将该结果存入数据库中
 * @Author: hong
 * @Date: 2017-09-27
 */
@Service
public class HandleResultService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private GetAuthentication getAuthentication;

    @Transactional
    public void HandleResult(ContactAndFriends contactAndFriends){
        //获取认证用户的账号
        String userNumber = getAuthentication.getUserNumber();
        //设置Client中字段值
        contactAndFriends.getClient().setUser_number(userNumber);
        contactAndFriends.getClient().setFriend_total(contactAndFriends.getMemberCount());
        System.out.println("----------------------- " + contactAndFriends.getMemberCount());
        contactAndFriends.getClient().setFriend_count(contactAndFriends.getMemberList().size());
        contactAndFriends.getClient().setGroup_count(contactAndFriends.getGroupList().size());
        contactAndFriends.getClient().setPublic_count(contactAndFriends.getPublicList().size());
        contactAndFriends.getClient().setSend_success_count(contactAndFriends.getSendMsgSuccess().size());
        contactAndFriends.getClient().setSend_fail_count(contactAndFriends.getSendMsgFail().size());
        //向数据库中插入扫码者的信息
        clientMapper.addClient(contactAndFriends.getClient());
        //查看该扫码者在数据库中有没有扫码记录
        ClientCount clientCount = clientMapper.getClientCount(contactAndFriends.getClient().getUin(), userNumber);
        if (clientCount == null){
            //插入第一次扫码记录
            ClientCount clientCount1 = new ClientCount();
            clientCount1.setCount(1);
            clientCount1.setUin(contactAndFriends.getClient().getUin());
            clientCount1.setUser_number(userNumber);
            clientMapper.addClientCount(clientCount1);
        }else {
            //如果是第二次或超过二次者更新count列中的数据
            clientCount.setCount(clientCount.getCount() + 1);
            clientMapper.updateClientCount(clientCount);
        }
    }
}
