package com.qiu.mapper;

import com.github.pagehelper.Page;
import com.qiu.dto.UserPageQueryDTO;
import com.qiu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author qiu
 * @version 1.0
 */
@Mapper
public interface UserMapper {


    /**
     * 根据用户姓名查询用户信息
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUserName(String username);


    /**
     * 注册成功，在数据库添加用户信息/新增用户信息
     * @param ruser
     */
    @Insert("insert into user (username, password, name, createtime, updatetime) "+
            "VALUES " +
            "(#{username},#{password},#{name},#{createTime},#{updateTime})")
    void insert(User ruser);


    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);


    /**
     * 根据主键动态修改用户信息
     * @param user
     */
    void update(User user);





    /**
     * 根据用户id删除用户信息
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    void deleteByUserId(Long id);


}
