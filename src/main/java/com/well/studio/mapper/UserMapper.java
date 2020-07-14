package com.well.studio.mapper;

import com.well.studio.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 * @author zhaojie
 * @date 2020/07/14 15:01:46
 */
@Mapper
public interface UserMapper {

    /**
     * 从数据库里查找出用户信息
     * @param uuid 用户唯一标识
     * @return 用户实体
     */
    User getUserByUuid(@Param("uuid") String uuid);
}