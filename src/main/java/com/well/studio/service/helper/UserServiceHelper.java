package com.well.studio.service.helper;

import com.well.studio.dao.UserDao;
import com.well.studio.service.AbstractNoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统生成Service帮助类
 * @author admin
 * @date 2020/07/31
 */
@Service
public class UserServiceHelper extends AbstractNoTransactionalService {

    @Autowired
    private UserDao userDao;



}
