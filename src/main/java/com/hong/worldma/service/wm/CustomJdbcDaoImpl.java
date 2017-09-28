package com.hong.worldma.service.wm;

import com.hong.worldma.entity.wm.WmUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:   自定义获取用户和用户角色信息
 * @Author: hong
 * @Date: 2017-09-26
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl {

    //查找注册用户信息SQL
    private String usersByUsernameQuery = "select tel, username, password, enabled, user_number from wm_users where tel = ?";
    //查找用户拥有的角色信息SQL
    private String authoritiesByUsernameQuery = "select username,authority from wm_user_authorities where username = ?";

    //以下三个方法有JdbcDaoImpl的loadUserByUsername方法依次调用

    //重写JdbcDaoImpl类中的loadUsersByUsername方法，查询wm_users表获取注册用户详细信息
    @Override
    protected List<UserDetails> loadUsersByUsername(String tel) {
        return this.getJdbcTemplate().query(this.usersByUsernameQuery, new String[]{tel}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String tel = rs.getString(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                boolean enabled = rs.getBoolean(4);
                String user_number = rs.getString(5);
                return new WmUser(tel, username, password, enabled, user_number, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }

    //重写JdbcDaoImpl类中的loadUserAuthorities方法，查询wm_user_authorities表获取注册用户拥有的角色信息
    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return this.getJdbcTemplate().query(this.authoritiesByUsernameQuery, new String[]{username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }
        });
    }

    //最终将该用户详细信息放入Authentication中
    @Override
    protected UserDetails createUserDetails(String tel, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        WmUser wmUser = (WmUser) userFromUserQuery;
        String returnUsername = wmUser.getUsername();
        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = tel;
        }
        return new WmUser(tel, returnUsername, wmUser.getPassword(), wmUser.isEnabled(), wmUser.getUser_number(), true, true, true, combinedAuthorities);
    }
}
