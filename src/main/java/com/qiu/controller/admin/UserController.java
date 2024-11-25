package com.qiu.controller.admin;

import com.qiu.constant.JwtClaimsConstant;
import com.qiu.dto.UserDTO;
import com.qiu.dto.UserLoginDTO;
import com.qiu.dto.UserPageQueryDTO;
import com.qiu.entity.User;
import com.qiu.properties.JwtProperties;
import com.qiu.result.PageResult;
import com.qiu.result.Result;
import com.qiu.server.UserServer;
import com.qiu.utils.JwtUtil;
import com.qiu.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiu
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
@Api(tags = "用户相关接口")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServer userServer;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody User user){
        User ruser = new User();
        BeanUtils.copyProperties(user,ruser);
        User reuser = userServer.findByUser(ruser.getUsername());//根据用户账号名查询用户
        if(reuser == null){
            //用户不存在，注册成功
            userServer.register(ruser);
            return Result.success("注册成功");
        }
        return Result.error("该用户名已存在");

    }


    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO);
        User user = userServer.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        return Result.success(userLoginVO);

    }


    /**
     * 新增用户
     * @return
     */
    @PostMapping
    @ApiOperation("新增用户")
    public Result save(@RequestBody UserLoginDTO userLoginDTO){
        userServer.save(userLoginDTO);
        return Result.success();

    }


    /**
     * 用户分页查询
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("用户分页查询")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO){
        PageResult pageResult = userServer.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);

    }


    /**
     * 编辑用户信息
     * @param userDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑用户信息")
    public Result update(@RequestBody UserDTO userDTO){
        userServer.update(userDTO);
        return Result.success();

    }


    /**
     * 删除用户信息
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除用户信息")
    public Result delete(Long id){
        userServer.deleteUser(id);
        return Result.success();

    }


}
