package com.hong.worldma.service.wm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.worldma.dao.ClientFriendsMapper;
import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.dao.ClientMapper;
import com.hong.worldma.entity.wm.ClientFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-04
 */
@Service
public class ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientFriendsMapper clientFriendsMapper;

    public PageInfo<Client> getAllClient(Client client) {
        PageHelper.startPage(client.getPage(), client.getRows());
        List<Client> list = clientMapper.getAllClient();
        return new PageInfo<Client>(list);
    }

    public PageInfo<ClientFriends> getAllClientFriends(ClientFriends clientFriends) {
        PageHelper.startPage(clientFriends.getPage(), clientFriends.getRows());
        List<ClientFriends> list = clientFriendsMapper.getAllClientFriends(clientFriends.getClientID());
        return new PageInfo<ClientFriends>(list);
    }


}
