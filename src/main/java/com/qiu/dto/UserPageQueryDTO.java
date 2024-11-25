package com.qiu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDTO implements Serializable {

    //用户姓名
    private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
