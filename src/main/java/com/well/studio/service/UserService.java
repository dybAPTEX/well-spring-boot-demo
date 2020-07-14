package com.well.studio.service;

import com.well.studio.pojo.entity.User;

/**
 * 用户服务
 * @author zhaojie
 * @date 2020/07/14 14:47:13
 */
public interface UserService {

    /**
     * 根据用户唯一标识uuid查找出用户信息
     * @param uuid 用户唯一标识
     * @return 用户实体
     */
    User getUserByUuid(String uuid);
}