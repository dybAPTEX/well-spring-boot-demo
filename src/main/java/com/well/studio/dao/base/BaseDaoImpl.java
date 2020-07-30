package com.well.studio.dao.base;
import com.well.studio.enums.ExceptionType;
import com.well.studio.exception.BizException;
import com.well.studio.helper.DozerHelper;
import com.well.studio.mapper.base.Mapper;
import com.well.studio.model.base.AbstractBo;
import com.well.studio.so.base.AbstractSo;
import com.well.studio.util.*;
import com.well.studio.model.base.AbstractHistoryBo;
import com.well.studio.util.mybatisUtil.OperatorTypeEnum;
import com.well.studio.vo.base.AbstractVo;
import com.well.studio.vo.base.OperatorVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * dyb
 * weier工作室
 */
public abstract class BaseDaoImpl<T extends AbstractBo, H extends AbstractHistoryBo> implements BaseDao<T> {

    /**
     * 数据库insert操作 最大数
     */
    protected static final int BATCH_INSERT_LIMIT = 200;

    /**
     * 数据库in操作 最大数
     */
    protected static final int IN_SIZE_LIMIT = 999;

    /**
     * 日志对象
     */
    protected static final Logger LOG = LoggerFactory.getLogger(com.well.studio.dao.base.BaseDaoImpl.class);

    @Autowired
    protected DozerHelper dozer;

    /**
     * bo的mapper
     *
     * @return 提供基础mapper
     */
    protected abstract Mapper<T> getMapper();

    /**
     * boHistory的mapper
     *
     * @return 提供HistoryMapper
     */
    protected abstract Mapper<H> getHistoryMapper();

    /**
     * 是否需要记录history
     *
     * @return 返回是否需要记录history标志
     */
    protected abstract Boolean getRecordHistory();

    @SuppressWarnings("unchecked")
    private Class<H> getGenericHbClass() {
        return (Class<H>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

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
        if (getRecordHistory()) {
            recordInsertHistory(bo);
        }
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
        if (getRecordHistory()) {
            recordBatchInsertHistory(insertList);
        }
    }

    private void recordBatchInsertHistory(List<T> insertList) {
        List<H> historyList = new ArrayList<>();
        for (T insertBo : insertList) {
            historyList.add(buildInsertHistoryBo(insertBo));
        }
        List<List<H>> splitList = ListSplitUtil.splitList(historyList, BATCH_INSERT_LIMIT);
        for (List<H> innerList : splitList) {
            getHistoryMapper().batchInsert(innerList);
        }
    }

    private H buildInsertHistoryBo(T bo) {
        H history = dozer.convert(bo, getGenericHbClass());
        history.setRefId(bo.getId());
        history.setOperationTime(new Date());
        history.setOperator(bo.getCreator());
        history.setOperatorId(bo.getCreatorId());
        history.setOperatorType(OperatorTypeEnum.CREATE);
        return history;
    }

    /**
     * 更新
     */
    @Override
    public int update(T bo) {
        fillUpdateInfo4Bo(bo);
        if (getRecordHistory()) {
            recordUpdateHistory(bo);
        }
        int affectedRows = getMapper().update(bo);
        if (affectedRows == 0) {
            throw new BizException("该数据被其他用户更改，请刷新后再试", ExceptionType.OP_LOCK);
        }
        return affectedRows;
    }

    /**
     * 记录更新历史
     *
     * @param bo 修改后对象
     */
    public void recordUpdateHistory(T bo) {
        H history = dozer.convert(bo, getGenericHbClass());
        history.setRefId(bo.getId());
        history.setOperationTime(new Date());
        history.setOperator(bo.getUpdater());
        history.setOperatorId(bo.getUpdaterId());
        history.setOperatorType(OperatorTypeEnum.UPDATE);
        getHistoryMapper().insert(history);
    }

    /**
     * 记录插入历史
     *
     * @param bo 待插入对象
     */
    private void recordInsertHistory(T bo) {
        H history = dozer.convert(bo, getGenericHbClass());
        history.setRefId(bo.getId());
        history.setOperationTime(new Date());
        history.setOperator(bo.getCreator());
        history.setOperatorId(bo.getCreatorId());
        history.setOperatorType(OperatorTypeEnum.CREATE);
        getHistoryMapper().insert(history);
    }

    /**
     * 记录删除历史
     *
     * @param id 待删除对象Id
     */
    private void recordDeleteHistory(Long id) {
        T bo = getMapper().findBo(id);
        H history = buildDeleteHistory(bo);
        if (history == null) {
            return;
        }
        getHistoryMapper().insert(history);
    }

    /**
     * 记录删除历史集合
     *
     * @param idList 待删除对象Id集合
     */
    private void recordDeleteHistory(List<Long> idList) {
        List<H> historyList = new ArrayList<>();
        List<T> boList = getMapper().listBoByIdList(idList);
        boList.forEach(bo -> historyList.add(buildDeleteHistory(bo)));
        getHistoryMapper().batchInsert(historyList);
    }

    private H buildDeleteHistory(T bo) {
        if (bo == null) {
            return null;
        }
        H history = dozer.convert(bo, getGenericHbClass());
        history.setRefId(bo.getId());
        history.setOperationTime(new Date());
        OperatorVo operator = SystemContextHolder.getOperator();
        if (operator != null) {
            history.setOperator(operator.getLoginName());
            history.setOperatorId(operator.getId());
        } else {
            history.setOperator(null);
            history.setOperatorId(null);
        }
        history.setOperatorType(OperatorTypeEnum.DELETE);
        return history;
    }

    /**
     * 删除
     */
    @Override
    public int delete(Long id) {
        if (getRecordHistory()) {
            recordDeleteHistory(id);
        }
        return getMapper().delete(id);
    }

    /**
     * 根据id集合删除
     */
    @Override
    public int deleteByIdList(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }
        if (getRecordHistory()) {
            recordDeleteHistory(idList);
        }
        int retVal = CommonConstant.ZERO_INT;
        List<List<Long>> splitList = ListSplitUtil.splitList(idList, IN_SIZE_LIMIT);
        for (List<Long> innerList : splitList) {
            retVal += getMapper().deleteByIdList(innerList);
        }
        return retVal;
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
    protected void fillUpdateInfo4Bo(AbstractBo bo) {
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
        for (AbstractBo bo : boList) {
            fillCreateInfo4Bo(bo);
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
