package com.hong.worldma.service.wm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.worldma.dao.ActivityImgPathMapper;
import com.hong.worldma.dao.ActivityMsgMapper;
import com.hong.worldma.dto.ServerResponse;
import com.hong.worldma.entity.wm.ActivityImgPath;
import com.hong.worldma.entity.wm.ActivityMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-08-31
 */
@Service
public class ActivityMsgService {
    private static Logger logger = LoggerFactory.getLogger(ActivityMsgService.class);

    @Autowired
    private ActivityMsgMapper activityMsgMapper;
    @Autowired
    private ActivityImgPathMapper activityImgPathMapper;
    @Value("${img_path}")
    private String imgPath;

    public ServerResponse<ActivityMsg> insertTextMsg(ActivityMsg msg) {
        logger.info("--------------添加文字消息的请求参数 = " + msg);
        if (!StringUtils.hasText(msg.getMsg())) {
            return ServerResponse.createByErrorCodeMessage(2, "活动消息不能为空");
        }
        //将用户添加的消息添加到数据库
        int count = activityMsgMapper.addMsg(msg);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("活动消息添加失败");
        }
        return ServerResponse.createBySuccess("活动消息添加成功", msg);
    }

    public ServerResponse<String> updateStatus(Integer id, String status) {
        logger.info("--------------更新消息发送状态请求参数 ID = " + id + " , status = " + status);
        if (!(StringUtils.hasText(status) && (status.equals("1") || status.equals("2")))) {
            return ServerResponse.createByErrorCodeMessage(2, "非法的请求参数");
        }
        int count = activityMsgMapper.updateStatus(id, status);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("活动消息状态更新失败");
        }
        return ServerResponse.createBySuccessMessage("活动消息状态更新成功");
    }

    public PageInfo<ActivityMsg> getAllMsg(ActivityMsg msg) {
        PageHelper.startPage(msg.getPage(), msg.getRows());
        List<ActivityMsg> list = activityMsgMapper.getUserAllMsg();
        return new PageInfo<ActivityMsg>(list);
    }

    @Transactional
    public ServerResponse<ActivityMsg> insertImgActivityMsg(MultipartFile files) {
        String imgPathName = imgPath;//文件保存路径
        File file = new File(imgPathName);
        if (!file.exists()) {
            boolean b = file.mkdir();
            if (!b) {
                return ServerResponse.createByErrorMessage("不能添加图片消息，因为不能再C盘下创建ImgActivityMsg文件夹");
            }
        }
        UUID uuid = UUID.randomUUID();
        String fileName = imgPathName + uuid + files.getOriginalFilename(); //保存文件在服务器中的地址（文件名是：UUID加原始文件名）
        try {
            files.transferTo(new File(imgPathName + uuid + files.getOriginalFilename()));//文件保存
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("添加图片活动消息失败");
        }
        //将用户添加的消息添加到数据库
        ActivityMsg msg = new ActivityMsg();
        msg.setType("2");
        msg.setMsg(files.getOriginalFilename());
        int count = activityMsgMapper.addMsg(msg);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("添加图片活动消息失败");
        }
        //将图片消息在服务器中存放的路径保存服务器
        ActivityImgPath activityImgPath = new ActivityImgPath();
        activityImgPath.setActivity_img_path(fileName);
        activityImgPath.setActivity_msg_id(msg.getId());
        int con = activityImgPathMapper.addImgPath(activityImgPath);
        if (con == 0){
            throw new RuntimeException();
        }
        return ServerResponse.createBySuccess("添加图片活动消息成功", msg);
    }

    @Transactional
    public ServerResponse<String> deleteActivityMsg(int id){
        //获取该消息的类型
        String msgType = activityMsgMapper.getMsgType(id);
        //删除activity_msg表中的该消息
        int count = activityMsgMapper.deleteActivityMsg(id);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("活动消息删除失败");
        }
        if (msgType.equals("2")) {
            //查找数据库activity_img_path 表，获取该ID代表的图片所在服务器的路径
            String imgPath = activityImgPathMapper.getImgPath(id);
            if (imgPath != null) {
                File file = new File(imgPath);
                if (file.exists() && file.isFile()) file.delete();
            }else {
                throw new RuntimeException();
            }
            //删除activity_img_path表中的该图片路径消息
            int con = activityImgPathMapper.deleteImgPath(id);
            if (con == 0) {
                throw new RuntimeException();
            }
        }
        return ServerResponse.createBySuccessMessage("活动消息删除成功");
    }
}