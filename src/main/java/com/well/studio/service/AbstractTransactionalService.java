package com.well.studio.service;


import com.well.studio.helper.DozerHelper;
import com.well.studio.mapper.base.JsonMapper;
import com.well.studio.mapper.base.JsonMapperFactory;
import com.well.studio.util.SystemContextHolder;
import com.well.studio.vo.base.OperatorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础的抽象servie实现 提供 <code>Transactional</code> 事务 <code>DozerHelper</code> dozer 服务
 * <code>org.slf4j.Logger</code> <code>JsonMapper</code> json 服务
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractTransactionalService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozer;

    protected DozerHelper getDozer() {
        return dozer;
    }

    /**
     * 获取登录用户信息
     *
     * @return 用户
     */
    public OperatorVo getOperator() {
        return SystemContextHolder.getOperator();
    }

}
