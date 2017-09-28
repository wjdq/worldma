package com.hong.worldma.dao;

import com.hong.worldma.entity.wm.ClientFriends;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-03
 */
@Mapper
public interface ClientFriendsMapper {
    @Insert("insert into client_friends(nick_name, sex, city, remark_name, client_id) values(#{nickName}, #{sex}, #{city}, #{remarkName}, #{clientID})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addClientFriends(ClientFriends client);

    @Select("select id, nick_name, remark_name, sex, city, createtime from client_friends where client_id = #{clientID}")
    List<ClientFriends> getAllClientFriends(Integer clientID);
}
