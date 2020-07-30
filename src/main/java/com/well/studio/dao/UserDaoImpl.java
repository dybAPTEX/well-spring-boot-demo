package com.well.studio.dao;

import com.well.studio.dao.base.BaseNoHistoryDaoImpl;
import com.well.studio.mapper.base.Mapper;
import com.well.studio.mapper.UserMapper;
import com.well.studio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统生成Dao实现类
 * @author admin
 * @date 2017/11/17
 */
@Repository
public class UserDaoImpl extends BaseNoHistoryDaoImpl<User> implements UserDao {

    @Autowired
    private UserMapper mapper;

    @Override
    protected Mapper<User> getMapper() {
        return mapper;
    }

}
