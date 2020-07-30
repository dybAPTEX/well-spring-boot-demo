package com.well.studio.service.common;

import com.well.studio.util.PageList;
import com.well.studio.so.UserSo;
import com.well.studio.vo.UserVo;

/**
 * 系统生成Service类
 * @author admin
 * @date 2020/07/30
 */
public interface UserService {

    /**
     * 新增
     * @param user 新增对象
     * @return 新增对象的id
     */
    Long create(UserVo user);

    /**
     * 删除
     * @param id 删除对象的id
     */
    void delete(Long id);

    /**
     * 修改
     * @param user 修改对象
     */
    void update(UserVo user);

    /**
     * 单个查询
     * @param id 查询对象id
     * @return 查询结果
     */
    UserVo find(Long id);

    /**
     * 按条件查询
     * @param userSo 查询条件
     * @return 查询结果
     */
    PageList<UserVo> listPagination(UserSo userSo);



}

