package com.well.studio.exception;


import com.well.studio.enums.ExceptionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * dyb
 * weier工作室
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException  extends RuntimeException{
    private static final long serialVersionUID = 7935408141184859889L;

    private boolean showExceptionMessage2user = true;

    private ExceptionType exceptionType;

    /**
     * 原始错误信息
     */
    private String orgErrorMessage;

    private Map<String, String> errorMap = new HashMap<String, String>();

    public BizException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
        this.orgErrorMessage = message;
    }

    public BizException(String message, ExceptionType exceptionType, boolean showExceptionMessage2user) {
        super(message);
        this.exceptionType = exceptionType;
        this.showExceptionMessage2user = showExceptionMessage2user;
        this.orgErrorMessage = message;

    }

    public BizException(String message, Throwable cause, ExceptionType exceptionType,
                        boolean showExceptionMessage2user) {
        super(message, cause);
        this.exceptionType = exceptionType;
        this.showExceptionMessage2user = showExceptionMessage2user;
        this.orgErrorMessage = message;

    }

}
