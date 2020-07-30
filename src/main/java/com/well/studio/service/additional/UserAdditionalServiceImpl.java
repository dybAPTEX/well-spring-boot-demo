package com.well.studio.service.additional;

import com.well.studio.dao.UserDao;
import com.well.studio.service.AbstractTransactionalService;
import com.well.studio.service.helper.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统生成AdditionalServiceImpl
 * @author admin
 * @date 2020/07/30
 */
@Service
public class UserAdditionalServiceImpl extends AbstractTransactionalService implements UserAdditionalService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserServiceHelper userServiceHelper;



}
