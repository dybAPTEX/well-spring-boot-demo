package com.well.studio.service.export;

import com.well.studio.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 系统生成导出类
 * @author admin
 * @date 2020/07/30
 */
@Service
public class ExportUser {

    private static final String TEMPLATE = "BP_USER.xml";
    private static final String STORE_NAME = "BP_USER";

    @Autowired
    private UserDao userDao;



}
