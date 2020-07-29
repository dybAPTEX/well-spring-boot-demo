package com.well.studio.enums;

public enum  ExceptionType {
    /**
     * 未定义
     */
    NOT_BIZ("未定义"),
    /**
     * 乐观锁
     */
    OP_LOCK("乐观锁"),
    /**
     * 数据库参数错误
     */
    DB_PARAM("数据库参数错误"),
    /**
     * 接口参数错误
     */
    INTERFACE_PARAM("接口参数错误"),
    /**
     * 没有资源
     */
    NO_RESOURCE("没有资源"),
    /**
     * 没有结果
     */
    NO_RESULT("没有结果"),
    /**
     * 违反业务规则
     */
    VIOLATE_BIZ_RULE("违反业务规则"),
    /**
     * 未通过业务检查
     */
    VIOLATE_BIZ_CHECK("未通过业务检查"),
    /**
     * 权限
     */
    SECURITY("权限"),
    /**
     * 导入导出
     */
    IMPORT_EXPORT("导入导出"),
    /**
     * 重复提交
     */
    DUPLICATE_SUBMIT("重复提交"),
    /**
     * 导入导出
     */
    DB_RET_ERROR("导入导出"),
    /**
     * 存在脏数据
     */
    DIRTY_DATA("存在脏数据"),
    /**
     * 存在脏数据
     */
    X6_ERROR("调用X6接口错误"),
    /**
     * e签宝签署失败
     */
    SIGN_ERROR("e签宝签署失败"),
    /**
     * 调用风控接口错误
     */
    RISK_ERROR("调用风控接口错误"),
    /**
     * 熔断器降级
     */
    HYSTRIX_FALL_BACK("熔断器降级"),
    /**
     * 银行回单接口失败
     */
    RECEIPT_SERVICE_ERROR("银行回单接口失败"),

    /**
     * 调用金字塔接口错误
     */
    PYRAMID_ERROR("调用金字塔接口错误"),

    /**
     * 赋强公证接口返回失败
     */
    DOSSIER_SERVICE_ERROR("赋强公证接口返回失败"),

    /**
     * 报单错误强提醒
     */
    BIZ_APPLICATION_ERROR("报单错误强提醒");

    private String name;

    ExceptionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
