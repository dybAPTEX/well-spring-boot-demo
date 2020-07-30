package com.well.studio.dao.base;

import com.github.pagehelper.PageHelper;
import com.well.studio.enums.ExceptionType;
import com.well.studio.exception.BizException;
import com.well.studio.helper.DozerHelper;
import com.well.studio.mapper.base.Mapper;
import com.well.studio.model.base.AbstractBo;
import com.well.studio.so.base.AbstractSo;
import com.well.studio.util.*;
import com.well.studio.vo.base.AbstractVo;
import com.well.studio.vo.base.OperatorVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * dyb
 * weier工作室
 */
public abstract class BaseNoHistoryDaoImpl<T extends AbstractBo> implements BaseDao<T> {

    /**
     * 数据库insert操作 最大数
     */
    private static final int BATCH_INSERT_LIMIT = 200;

    /**
     * 数据库in操作 最大数
     */
    private static final int IN_SIZE_LIMIT = 999;

    @Autowired
    protected DozerHelper dozer;

    /**
     * bo的mapper
     */
    protected abstract Mapper<T> getMapper();

    /**
     * 填充创建人和创建时间
     *
     * @param bo 创建对象
     */
    private int doInsert(T bo) {
        fillCreateInfo4Bo(bo);
        return getMapper().insert(bo);
    }

    /**
     * 插入 并返回生成的序列ID
     */
    @Override
    public Long insert(T bo) {
        doInsert(bo);
        return bo.getId();
    }

    /**
     * 批量插入
     */
    @Override
    public void batchInsert(List<T> insertList) {
        if (CollectionUtils.isEmpty(insertList)) {
            return;
        }
        fillCreateInfo4BoList(insertList);
        List<List<T>> splitList = ListSplitUtil.splitList(insertList, BATCH_INSERT_LIMIT);
        for (List<T> innerList : splitList) {
            getMapper().batchInsert(innerList);
        }
    }

    /**
     * 更新
     */
    @Override
    public int update(T bo) {
        fillUpdateInfo4Bo(bo);
        int affectedRows = getMapper().update(bo);
        if (affectedRows == 0) {
            throw new BizException("该数据被其他用户更改，请刷新后再试", ExceptionType.OP_LOCK);
        }
        return affectedRows;
    }

    /**
     * 删除
     */
    @Override
    public int delete(Long id) {
        return getMapper().delete(id);
    }

    /**
     * 根据id集合删除
     */
    @Override
    public int deleteByIdList(List<Long> idList) {
        return getMapper().deleteByIdList(idList);
    }

    /**
     * 根据id查找
     */
    @Override
    public T findBo(Long id) {
        return getMapper().findBo(id);
    }

    /**
     * 查询
     *
     * @param so 查询条件
     * @return Bo 集合
     */
    @Override
    public List<T> listBoBySo(AbstractSo so) {
        return getMapper().listBoBySo(so);
    }

    /**
     * 查询id
     *
     * @param so 查询条件
     * @return Bo 集合
     */
    @Override
    public List<Long> listIdBySo(AbstractSo so) {
        return getMapper().listIdBySo(so);
    }

    /**
     * 查询总数
     */
    @Override
    public int countBySo(AbstractSo so) {
        return getMapper().countBySo(so);
    }

    /**
     * 根据id查找
     */
    @Override
    public <U extends AbstractVo> U findVo(Long id) {
        return getMapper().findVo(id);
    }

    /**
     * 查询
     *
     * @param so 查询条件
     * @return Vo集合
     */
    @Override
    public <U extends AbstractVo> List<U> listVoBySo(AbstractSo so) {
        return getMapper().listVoBySo(so);
    }

    /**
     * 分页查询
     *
     * @param so 查询条件
     * @return Vo 集合
     */
    @Override
    public <U extends AbstractVo> List<U> listPaginationVoBySo(AbstractSo so) {
        PageHelper.startPage(so.getPageNumber(), so.getPageSize());
        return getMapper().listVoBySo(so);
    }

    /**
     * 根据id集合查询对象 返回Bo集合
     */
    @Override
    public List<T> listBoByIdList(List<Long> idList) {
        List<T> res = new ArrayList<>();
        List<List<Long>> splitList = ListSplitUtil.splitList(idList, IN_SIZE_LIMIT);
        for (List<Long> innerIdList : splitList) {
            List<T> innerList = getMapper().listBoByIdList(innerIdList);
            res.addAll(innerList);
        }
        return res;
    }

    /**
     * 根据id集合查询对象 返回Vo集合
     */
    @Override
    public <U extends AbstractVo> List<U> listVoByIdList(List<Long> idList) {
        List<U> res = new ArrayList<>();
        List<List<Long>> splitList = ListSplitUtil.splitList(idList, IN_SIZE_LIMIT);
        for (List<Long> innerIdList : splitList) {
            List<U> innerList = getMapper().listVoByIdList(innerIdList);
            res.addAll(innerList);
        }
        return res;
    }

    /**
     * 填充更新人和更新时间
     */
    private void fillUpdateInfo4Bo(AbstractBo bo) {
        OperatorVo operator = SystemContextHolder.getOperator();

        updateInfoFill(operator, bo);

        bo.setUpdatedTime(new Date());
    }

    /**
     * 填充创建人和创建时间
     */
    private void fillCreateInfo4Bo(AbstractBo bo) {
        OperatorVo operator = SystemContextHolder.getOperator();

        createInfoFill(operator, bo);

        bo.setCreatedTime(new Date());

        if (bo.getLockVersion() == null) {
            bo.setLockVersion(0);
        }
    }

    /**
     * 填充创建人和创建时间
     */
    private void fillCreateInfo4BoList(List<T> boList) {
        OperatorVo operator = SystemContextHolder.getOperator();
        for (AbstractBo bo : boList) {

            createInfoFill(operator, bo);

            bo.setCreatedTime(new Date());

            if (bo.getLockVersion() == null) {
                bo.setLockVersion(0);
            }
        }
    }

    protected <V extends AbstractVo, S extends AbstractSo> V findVoBySoBase(S so) {
        List<V> list = getMapper().listVoBySo(so);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw new BizException("findVoBySoBase：【" + so.toString() + "】查找到多个对象", ExceptionType.DIRTY_DATA);
        }
        return list.get(0);
    }

    protected <S extends AbstractSo> T findBoBySoBase(S so) {
        List<T> list = getMapper().listBoBySo(so);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw new BizException("findBoBySoBase：【" + so.toString() + "】查找到多个对象", ExceptionType.DIRTY_DATA);
        }
        return list.get(0);
    }
}
