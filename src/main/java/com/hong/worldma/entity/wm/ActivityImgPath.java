package com.hong.worldma.entity.wm;

/**
 * @Description:    数据库 activity_img_path 表，用来保存用户上传的图片消息在服务器中存储的路径，
 *                          通过该路径在用户删除图片消息时，同时在服务器删除该图片
 * @Author: hong
 * @Date: 2017-09-24
 */
public class ActivityImgPath {
    //字段说明见数据库字段描述
    private Integer id;
    private String activity_img_path;
    private Integer activity_msg_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivity_img_path() {
        return activity_img_path;
    }

    public void setActivity_img_path(String activity_img_path) {
        this.activity_img_path = activity_img_path;
    }

    public Integer getActivity_msg_id() {
        return activity_msg_id;
    }

    public void setActivity_msg_id(Integer activity_msg_id) {
        this.activity_msg_id = activity_msg_id;
    }
}
