package com.hong.worldma.service.wm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.dao.ClientMapper;
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

    public PageInfo<Client> getAllClient(Client client) {
        PageHelper.startPage(client.getPage(), client.getRows());
        List<Client> list = clientMapper.getAllClient();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++){
                int count = clientMapper.getClientCount(list.get(i).getUin()).getCount();
                list.get(i).setCount(count);
            }
        }
        return new PageInfo<Client>(list);
    }
}
