package com.hong.worldma.entity.wm;

import java.util.Date;

/**
 * @Description:   对应数据库中 client表
 * @Author: hong
 * @Date: 2017-09-03
 */
public class Client extends BaseEntity{
    //各字段信息详见client表列字段描述
    private Integer id;
    private String nick_name;
    private String sex;
    private String city;
    private Integer friend_total;
    private String uin;
    private int send_success_count;
    private int send_fail_count;
    private Integer count;
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getFriend_total() {
        return friend_total;
    }

    public void setFriend_total(Integer friend_total) {
        this.friend_total = friend_total;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public int getSend_success_count() {
        return send_success_count;
    }

    public void setSend_success_count(int send_success_count) {
        this.send_success_count = send_success_count;
    }

    public int getSend_fail_count() {
        return send_fail_count;
    }

    public void setSend_fail_count(int send_fail_count) {
        this.send_fail_count = send_fail_count;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
