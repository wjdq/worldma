package com.hong.worldma.web.wx;

import com.hong.worldma.service.wx.CutInService;
import com.hong.worldma.dto.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
@RestController
@RequestMapping("/wx")
public class CutInController {
    @Autowired
    private CutInService cutInService;

    @RequestMapping("/getUUID")
    public ServerResponse<String> qrCode(){
        return cutInService.getQRcode();
    }
}
