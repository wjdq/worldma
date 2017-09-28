package com.hong.worldma.service.wm;

import com.hong.worldma.entity.wm.WmUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @Description: //获取认证用户的身份信息
 * @Author: hong
 * @Date: 2017-09-21
 */
@Service
public class GetAuthentication {

    //获取认证用户的登录时输入的账号
    public String getUserNumber(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNumber;
        if (principal instanceof WmUser) {
            userNumber = ((WmUser)principal).getUser_number();
        } else {
            userNumber = principal.toString();
        }
        return userNumber;
    }
}
