package com.hong.worldma.web.wm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-22
 */
@Controller
@RequestMapping("/wm")
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/add_activityMsg")
    public String activityMsg(){
        return "activity_msg";
    }
}
