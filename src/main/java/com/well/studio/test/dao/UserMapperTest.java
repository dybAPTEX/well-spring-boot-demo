package com.well.studio.test.dao;

import com.well.studio.mapper.base.Mapper;
import com.well.studio.mapper.UserMapper;
import com.well.studio.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统生成Mapper测试类
 * @author admin
 * @date 2020/07/30
 */
public class UserMapperTest  {

    @Autowired
    private UserMapper userMapper;

    protected Mapper<User> getDAO() {
        return userMapper;
    }
}
