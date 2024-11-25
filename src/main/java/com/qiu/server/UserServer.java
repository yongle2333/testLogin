package com.qiu.server;

import com.qiu.dto.UserDTO;
import com.qiu.dto.UserLoginDTO;
import com.qiu.dto.UserPageQueryDTO;
import com.qiu.entity.User;
import com.qiu.result.PageResult;
import org.springframework.stereotype.Service;

/**
 * @author qiu
 * @version 1.0
 */

@Service
public interface UserServer {


    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);


    /**
     * 用户注册
     * @param ruser
     */
    void register(User ruser);


    /**
     * 根据账户名查询账户信息
     * @param username
     * @return
     */
    User findByUser(String username);


    /**
     * 新增用户
     * @param userLoginDTO
     */
    void save(UserLoginDTO userLoginDTO);


    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);


    /**
     * 编辑用户信息
     * @param userDTO
     */
    void update(UserDTO userDTO);




    /**
     * 删除用户信息
     */
    void deleteUser(Long id);

}
