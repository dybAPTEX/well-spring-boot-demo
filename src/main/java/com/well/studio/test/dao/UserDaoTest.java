package com.well.studio.test.dao;

import com.well.studio.dao.UserDao;
import com.well.studio.dao.base.BaseDao;
import com.well.studio.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统生成dao测试类
 * @author admin
 * @date 2020/07/30
 */
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    protected BaseDao<User> getDAO() {
        return userDao;
    }
}
