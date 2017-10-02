package com.hong.worldma.entity.wm;

import java.util.Date;

/**
 * @Description:  记录该客户是第几次扫码   对应数据库  client_count表
 * @Author: hong
 * @Date: 2017-09-27
 */
public class ClientCount {
    //各字段信息详见client_count表列字段描述
    private Integer id;
    private Integer count;
    private String uin;
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
