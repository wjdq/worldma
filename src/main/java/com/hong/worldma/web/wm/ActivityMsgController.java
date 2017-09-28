package com.hong.worldma.web.wm;

import com.github.pagehelper.PageInfo;

import com.hong.worldma.entity.wm.ActivityMsg;
import com.hong.worldma.dto.ServerResponse;
import com.hong.worldma.service.wm.ActivityMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-01
 */
@RestController
@RequestMapping("/manage")
public class ActivityMsgController {

    @Autowired
    private ActivityMsgService activityMsg;

    @RequestMapping(value="/add_text_activity_msg", method = RequestMethod.POST)
    public ServerResponse<ActivityMsg> addTextActivityMsg(ActivityMsg msg){
        return activityMsg.insertTextMsg(msg);
    }

    @RequestMapping(value="/update_msg_status", method = RequestMethod.POST)
    public ServerResponse<String> updateMsgStatus(@RequestParam("id") Integer id, @RequestParam("status") String status){
        return activityMsg.updateStatus(id, status);
    }

    @RequestMapping(value="/get_activity_info", method = RequestMethod.GET)
    public PageInfo<ActivityMsg> getActivityInfo(ActivityMsg msg){
        return activityMsg.getAllMsg(msg);
    }

    @RequestMapping(value="/add_img_activity_msg", method = RequestMethod.POST)
    public ServerResponse<ActivityMsg> addImgActivityMsg(@RequestParam("file") MultipartFile file){
        return activityMsg.insertImgActivityMsg(file);
    }

    @RequestMapping(value="/delete_activity_msg", method = RequestMethod.POST)
    public ServerResponse<String> deleteActivityMsg(@RequestParam(value="activityMsgID",required=true,defaultValue="0") int id){
        System.out.println("------------- id = " + id);
        return activityMsg.deleteActivityMsg(id);
    }
}
