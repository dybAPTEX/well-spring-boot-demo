package com.well.studio.service;


import com.well.studio.helper.DozerHelper;
import com.well.studio.mapper.base.JsonMapper;
import com.well.studio.mapper.base.JsonMapperFactory;
import com.well.studio.util.SystemContextHolder;
import com.well.studio.vo.base.OperatorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * creator by @author 2b 提供给Service，没有事物 提供 <code>DozerHelper</code> dozer 服务 <code>org.slf4j.Logger</code> log 服务
 * <code>JsonMapper</code> json 服务
 */
public abstract class AbstractNoTransactionalService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozerHelper;

    protected DozerHelper getDozerHelper() {
        return dozerHelper;
    }


}
