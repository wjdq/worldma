package com.hong.worldma.web.wm;

import com.github.pagehelper.PageInfo;

import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.entity.wm.ClientFriends;
import com.hong.worldma.service.wm.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-04
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value="/get_all_client", method = RequestMethod.GET)
    public PageInfo<Client> getAllClient(Client client){
        return clientService.getAllClient(client);
    }

    @RequestMapping(value="/get_client_friends", method = RequestMethod.GET)
    public PageInfo<ClientFriends> getAllClient(ClientFriends clientFriends){
        return clientService.getAllClientFriends(clientFriends);
    }
}
