package com.well.studio.service.common;

import com.well.studio.dao.UserDao;
import com.well.studio.model.User;
import com.well.studio.service.AbstractTransactionalService;
import com.well.studio.so.UserSo;
import com.well.studio.vo.UserVo;
import com.well.studio.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 系统生成Service实现类
 * @author admin
 * @date 2020/07/30
 */
@Service
public class UserServiceImpl extends AbstractTransactionalService implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 新增
     *
     * @param user 新增对象
     * @return 新增对象的id
     */
    @Override
    public Long create(UserVo user) {
        checkBeforeCreate(user);
        User userBo = dozer.convert(user, User.class);
        return userDao.insert(userBo);
    }

    private void checkBeforeCreate(UserVo user) {
        checkRequired(user);
        checkDuplicate4Create(user);
    }


    /**
     * 删除
     *
     * @param id 删除对象的id
     */
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    /**
     * 修改
     *
     * @param user 修改对象
     */
    @Override
    public void update(UserVo user) {
        checkBeforeUpdate(user);
        User userBo = dozer.convert(user, User.class);
        userDao.update(userBo);
    }

    private void checkBeforeUpdate(UserVo user) {
        checkRequired(user);
        checkDuplicate4Update(user);
    }

    /**
     * 单个查询
     *
     * @param id 查询对象id
     * @return 查询结果
     */
    @Override
    public UserVo find(Long id) {
        return userDao.findVo(id);
    }

    /**
     * 按条件查询
     *
     * @param userSo 查询条件
     * @return 查询结果
     */
    @Override
    public PageList<UserVo> listPagination(UserSo userSo) {
        List<UserVo> list = userDao.listPaginationVoBySo(userSo);
        int count = userDao.countBySo(userSo);
        return new PageList<>(list, count);
    }

    /**
     * 检查必填字段
     */
    private void checkRequired(UserVo user) {

    }

    /**
     * 检查字段唯一性（创建）
     */
    private void checkDuplicate4Create(UserVo user) {

    }



    /**
     * 检查字段唯一性（更新）
     */
    private void checkDuplicate4Update(UserVo user) {

    }





}
