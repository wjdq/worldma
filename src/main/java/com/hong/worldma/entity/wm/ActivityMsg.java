package com.hong.worldma.entity.wm;

import java.util.Date;

/**
 * @Description: 对应数据库中的activity_msg表
 * @Author: hong
 * @Date: 2017-08-31
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityMsg  extends BaseEntity{
    private Integer id;
    //待发送的活动消息
    private String msg;
    //消息的类型（1表示文字或 2表示图片）
    private String type;
    //活动消息的状态（1表示要发送的活动消息，2表示以过期的活动消息）
    private String status;
    //消息创建的时间
    private Date createtime;
    //添加消息的用户账号
    private String user_number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }
}
