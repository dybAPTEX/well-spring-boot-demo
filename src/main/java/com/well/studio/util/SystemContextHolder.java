package com.well.studio.util;


import com.alibaba.ttl.TransmittableThreadLocal;

import com.well.studio.enums.LoginTypeEnum;
import com.well.studio.vo.base.MobileOperatorVo;
import com.well.studio.vo.base.OperatorVo;
import com.well.studio.vo.base.PyramidUserVo;
import org.apache.commons.lang.StringUtils;

public class SystemContextHolder {
    private static ThreadLocal<SystemContext> contextHolder = new TransmittableThreadLocal<>();

    private SystemContextHolder() {

    }

    /**
     * 清理context
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * 得到context
     */
    public static SystemContext getContext() {
        if (contextHolder.get() == null) {
            contextHolder.set(new SystemContext());
        }
        return contextHolder.get();
    }

    /**
     * 装载context
     */
    public static void setContext(SystemContext context) {
        contextHolder.set(context);
    }

    /**
     * 构建context
     */
    public static void buildOperatorContext(OperatorVo operator) {
        setContextWebOperator(operator);
    }

    /**
     * 构建context
     */
    public static void buildOperatorContext(MobileOperatorVo operator) {
        setContextMobileOperator(operator);
    }

    /**
     * 构建context
     */
    public static void buildOperatorContext(PyramidUserVo operator) {
        setContextPyramidOperator(operator);
    }

    /**
     * 构建默认context
     */
    public static void buildDefaultContext() {
        OperatorVo operator = new OperatorVo();
        operator.setId(-1L);
        operator.setLoginName("admin");
        setContextWebOperator(operator);
    }

    /**
     * 得到登录名
     */
    public static String getOperatorLoginName() {
        return getOperator() != null ? getOperator().getLoginName() : StringUtils.EMPTY;
    }

    /**
     * 得到当前登录用户ID
     */
    public static Long getOperatorId() {
        return getOperator() != null ? getOperator().getId() : null;
    }

    /**
     * 得到当前登录客户ID
     */
    public static Long getClientId() {
        return getOperator() != null ? getOperator().getClientId() : null;
    }

    /**
     * 得到用户名
     */
    public static String getOperatorNameCn() {
        return getOperator() != null ? getOperator().getNameCn() : StringUtils.EMPTY;
    }

    /**
     * 得到 工号-姓名
     *
     * @return
     */
    public static String getLoginAndNameCn() {
        OperatorVo operator = getOperator();
        String keeper = null;
        if (operator != null) {
            keeper = operator.getLoginName() + "-" + operator.getNameCn();
        }
        return keeper;
    }

    /**
     * 得到用户
     */
    public static OperatorVo getOperator() {
        if (SystemContextHolder.getContext() != null) {
            return SystemContextHolder.getContext().getOperator();
        }
        return null;
    }

    /**
     * 重设context, 基础方法，提供给web和mobile使用
     */
    private static void setContextOperator(OperatorVo operator) {
        SystemContext systemContext = contextHolder.get();
        if (systemContext != null) {
            systemContext.setOperator(operator);
            return;
        }
        contextHolder.set(new SystemContext(operator));

    }

    /**
     * web端重设context
     */
    private static void setContextWebOperator(OperatorVo operator) {
        if (operator == null) {
            return;
        }
        operator.setLoginType(LoginTypeEnum.WEB);
        setContextOperator(operator);
    }

    /**
     * 设置sessionId
     */
    public static void setSessionId(String sessionId) {
        SystemContext systemContext = contextHolder.get();
        if (systemContext != null) {
            systemContext.setSessionId(sessionId);
            return;
        }
        SystemContext sc = new SystemContext();
        sc.setSessionId(sessionId);
        contextHolder.set(sc);
    }

    /**
     * 获取sessionId
     */
    public static String getSessionId() {
        if (SystemContextHolder.getContext() != null) {
            return SystemContextHolder.getContext().getSessionId();
        }
        return null;
    }

    /**
     * mobile端重设context
     */
    private static void setContextMobileOperator(MobileOperatorVo mobileOperator) {
        if (mobileOperator == null) {
            return;
        }
        OperatorVo operator = new OperatorVo();
        operator.setLoginName(mobileOperator.getUserCode());
        operator.setNameCn(mobileOperator.getClientName());
        operator.setNameEn(mobileOperator.getClientName());
        operator.setClientId(mobileOperator.getClientId());
        operator.setLoginType(LoginTypeEnum.MOBILE);
        operator.setLdap(false);
        setContextOperator(operator);
    }

    /**
     * 小贷H5端重设context
     */
    private static void setContextPyramidOperator(PyramidUserVo pyramidUserVo) {
        if (pyramidUserVo == null) {
            return;
        }
        OperatorVo operator = new OperatorVo();
        operator.setLoginName(pyramidUserVo.getUserName());
        operator.setSiteId(pyramidUserVo.getSiteId());
        operator.setSiteCode(pyramidUserVo.getSiteCode());
        operator.setSiteName(pyramidUserVo.getSiteName());
        operator.setCustomerType(pyramidUserVo.getCustomerType());
        operator.setLdap(false);
        setContextOperator(operator);
    }

}
