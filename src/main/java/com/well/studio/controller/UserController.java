package com.well.studio.controller;

import com.well.studio.pojo.entity.User;
import com.well.studio.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户接口
 * @author zhaojie
 * @date 2020/07/14 14:46:03
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/find")
    public User getByName(@RequestParam(value = "uuid") String uuid){
        return userService.getUserByUuid(uuid);
    }
}