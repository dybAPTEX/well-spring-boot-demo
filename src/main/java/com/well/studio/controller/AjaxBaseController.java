package com.well.studio.controller;


import com.well.studio.enums.ExceptionType;
import com.well.studio.exception.BizException;
import com.well.studio.helper.DozerHelper;
import com.well.studio.mapper.base.JsonMapper;
import com.well.studio.mapper.base.JsonMapperFactory;
import com.well.studio.vo.base.PackVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Collections;

/**
 * dyb
 * weier工作室
 */
public class AjaxBaseController {

    protected static final String CREATE = "/create";

    protected static final String UPDATE = "/update";

    protected static final String DELETE = "/delete";

    protected static final String FIND = "/find";

    protected static final String LIST = "/list";

    protected static final String LIST_PAGINATION = "/listPagination";

    protected static final String EXPORT = "/export";

    protected static final String IMPORT = "/import";

    protected static final String TERMINATE = "/terminate";

    protected static final String ACTIVE = "/active";

    protected static final String FROZEN = "/frozen";

    protected static final String INACTIVE = "/inactive";

    protected static final String LIST_PAGINATION_WITH_DATA_PERMISSION = "listPaginationWithDataPermission";

    protected static final Logger LOG = LoggerFactory.getLogger(AjaxBaseController.class);

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozer;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PackVo<Serializable> ajaxException(Exception e) {
        PackVo<Serializable> packVo = new PackVo<>();
        packVo.setSuccess(Boolean.FALSE);
        LOG.error("异常", e);
        if (e instanceof BizException) {
            packVo.setMessages(Collections.singletonList(e.getMessage()));
            if (((BizException) e).getExceptionType() != null){
                packVo.setUdf3(((BizException) e).getExceptionType().toString());
            }
            return packVo;
        }

        if (e instanceof MissingServletRequestParameterException
                || e instanceof NumberFormatException
                || e instanceof TypeMismatchException
                || e instanceof IllegalArgumentException
                || e instanceof SecurityException) {
            packVo.setMessages(Collections.singletonList(e.getMessage()));
            return packVo;
        }

        BizException bizException = new BizException("系统异常", e, ExceptionType.NOT_BIZ, false);
        packVo.setMessages(Collections.singletonList(bizException.getMessage()));
        return packVo;
    }

}
