package com.hong.worldma.dao;

import com.hong.worldma.entity.wm.ActivityMsg;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description:   //activity_msg表
 * @Author: hong
 * @Date: 2017-08-31
 */
@Mapper
public interface ActivityMsgMapper {
    //插入文字或图片消息
    @Insert("insert into activity_msg(id, msg, type, user_number) values(#{id}, #{msg}, #{type}, #{user_number})")
    @Options(useGeneratedKeys=true, keyProperty="id")//插入成功后获取该条记录的主键ID
    int addMsg(ActivityMsg msg);

    //更新消息发送的状态（1  发送，2 不发送）
    @Update("update activity_msg set status = #{status} where id = #{id} and user_number = #{user_number}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status, @Param("user_number") String user_number);

    //获取自己发布的所有活动消息
    @Select("select id, msg, type, status, createtime from activity_msg where user_number = #{user_number}")
    List<ActivityMsg> getUserAllMsg(String user_number);

    //群发消息时获取Status = 1 类型的消息
    @Select("select id, msg, type from activity_msg where status = 1 and user_number = #{user_number}")
    List<ActivityMsg> getStatus_One(String user_number);

    //获取该消息的类型信息
    @Select("select type from activity_msg where id = #{id}")
    String getMsgType(Integer id);

    //查找当前用户发布的活动消息条数
    @Select("select count(*) from activity_msg where user_number = #{user_number}")
    Integer userActivityMsgCount(String user_number);

    //用户删除不需要发送的消息
    @Delete("delete from activity_msg where id = #{id} and user_number = #{user_number}")
    int deleteActivityMsg(@Param("id") int id, @Param("user_number") String user_number);
}
