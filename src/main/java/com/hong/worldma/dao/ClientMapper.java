package com.hong.worldma.dao;


import com.hong.worldma.entity.wm.Client;
import com.hong.worldma.entity.wm.ClientCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description:   CRUD client表
 * @Author: hong
 * @Date: 2017-09-03
 */
@Mapper
public interface ClientMapper {

    //向client表插入扫码者的信息
    @Insert("insert into client(nick_name, sex, city, friend_total, uin, send_success_count, send_fail_count) values(#{nick_name}, #{sex}, #{city}, #{friend_total}, #{uin}, #{send_success_count}, #{send_fail_count})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int addClient(Client client);

    //向client_count表插入扫码者的扫码次数
    @Insert("insert into client_count(count, uin) values(#{count}, #{uin})")
    int addClientCount(ClientCount clientCount);

    //更新client_count表count数据
    @Update("update client_count set count=#{count} where uin = #{uin}")
    int updateClientCount(ClientCount clientCount);


    @Select("select count, uin from client_count where uin = #{uin}")
    ClientCount getClientCount(@Param("uin") String uin);

    //查找扫码者信息
    @Select("select id, nick_name, sex, city, friend_total, uin, send_success_count, send_fail_count, createtime from client order by createtime desc")
    List<Client> getAllClient();
}
