package com.qiu.server.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiu.constant.MessageConstant;
import com.qiu.constant.NameConstant;
import com.qiu.constant.PasswordConstant;
import com.qiu.context.BaseContext;
import com.qiu.dto.UserDTO;
import com.qiu.dto.UserLoginDTO;
import com.qiu.dto.UserPageQueryDTO;
import com.qiu.entity.User;
import com.qiu.exception.AccountNotFoundException;
import com.qiu.exception.PasswordErrorException;
import com.qiu.mapper.UserMapper;
import com.qiu.result.PageResult;
import com.qiu.server.UserServer;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qiu
 * @version 1.0
 */
@Service
public class UserServerImpl implements UserServer {

    @Autowired
    private UserMapper userMapper;


    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1.根据用户名查询数据库中的数据
        User user = userMapper.getByUserName(username);
        //2.处理各种异常情况(用户名不存在，密码错误等)
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //3.密码比对，对于前端传过来的密码进行md5加密  **********
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(user.getPassword())){
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //返回实体对象
        return user;
    }

    /**
     * 用户注册
     * @param ruser
     */
    @Override
    public void register(User ruser) {
        //设置密码，默认123456,要用md5加密
        ruser.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        ruser.setName(NameConstant.DEFAULT_NAME);   //姓名默认为顶真
        ruser.setCreateTime(LocalDateTime.now());
        ruser.setUpdateTime(LocalDateTime.now());
        userMapper.insert(ruser);
    }


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findByUser(String username) {
        User user = userMapper.getByUserName(username);
        return user;
    }


    /**
     * 新增用户
     * @param userLoginDTO
     */
    @Override
    public void save(UserLoginDTO userLoginDTO) {
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO,user);
        //设置密码，默认123456,要用md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);

    }


    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        //select * from employee limit 0,10     PageHelper底层基于ThreadLocal实现
        //开始分页查询
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.pageQuery(userPageQueryDTO);
        long total = page.getTotal();
        List<User> records = page.getResult();
        return new PageResult(total,records);

    }


    /**
     * 编辑/更新用户信息
     * @param userDTO
     */
    @Override
    public void update(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);

        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }


    /**
     * 删除用户信息
     */
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByUserId(id);
    }
}
