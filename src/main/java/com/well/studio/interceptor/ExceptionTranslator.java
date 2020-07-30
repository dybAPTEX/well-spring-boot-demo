package com.well.studio.interceptor;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.well.studio.enums.ExceptionType;
import com.well.studio.exception.BizException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @program: well-spring-boot-demo
 * @description: 接口层异常常用切面
 * @author: daiyunbo
 * @create: 2020-07-30 19:08
 **/
@Aspect
@Component
public class ExceptionTranslator {

    /**
     *  execution()	表达式的主体
     * 第一个“*”符号	表示返回值的类型任意
     * com.well.studio.controller	AOP所切的服务的包名，即，需要进行横切的业务类
     * 包名后面的“..”	表示当前包及子包
     * 第二个“*”	表示类名，*即所有类
     * .*(..)	表示任何方法名，括号表示参数，两个点表示任何参数类型
     */
    @AfterThrowing(pointcut = "execution(public * com.well.studio.controller..*.*(..))", throwing = "e")
    public void translateException(RuntimeException e) {
        if (e instanceof BizException) {
            throw e;
        } else if (e instanceof HystrixRuntimeException) {
            throw new BizException("系统繁忙，请稍后再试", e, ExceptionType.HYSTRIX_FALL_BACK, true);
        } else {
            throw new BizException("系统异常(Controller捕获(Runtime))", e, ExceptionType.NOT_BIZ, false);
        }
    }

}
