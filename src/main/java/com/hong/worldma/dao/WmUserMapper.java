package com.hong.worldma.dao;

import com.hong.worldma.entity.wm.WmUser;
import org.apache.ibatis.annotations.*;

/**
 * @Description:   //CRUD  wm_users表
 * @Author: hong
 * @Date: 2017-09-26
 */
@Mapper
public interface WmUserMapper {
    //插入World码注册用户信息
    @Insert("insert into wm_users(username, tel, email, password, user_number) values(#{username}, #{tel}, #{email}, #{password}, #{user_number})")
    int addWmUser(WmUser user);

    //获取World码注册用户信息
    @Select("select tel, password, user_number, enabled from wm_users where tel = #{tel}")
    WmUser getUser(String tel);




    //
//    @Delete("delete from activity_img_path where activity_msg_id = #{activity_msg_id} and user_number = #{user_number}")
//    int deleteImgPath(@Param("activity_msg_id") Integer id, @Param("user_number") String user_number);
}
