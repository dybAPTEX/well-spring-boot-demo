package com.well.studio.service.impl;

import com.well.studio.mapper.UserMapper;
import com.well.studio.pojo.entity.User;
import com.well.studio.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 * @author zhaojie
 * @date 2020/07/14 14:59:50
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUuid(String uuid) {
        return userMapper.getUserByUuid(uuid);
    }
}