package com.well.studio.util.util2;
/**
 * @author bl02780
 * @date 2017/11/14
 */
public enum OperatorTypeEnum {

    /**
     * 新建
     */
    CREATE("新建"),

    /**
     * 更新
     */
    UPDATE("更新"),

    /**
     * 删除
     */
    DELETE("删除");

    private String name;

    OperatorTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
