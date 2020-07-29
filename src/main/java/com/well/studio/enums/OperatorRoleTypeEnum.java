package com.well.studio.enums;

public enum OperatorRoleTypeEnum {
    /**
     * 业务经理
     */
    BUSINESS_MANAGER("业务经理"),
    /**
     * 一级合作商
     */
    ONE_COOPERATION("一级合作商"),
    /**
     * 二级合作商
     */
    TWO_COOPERATION("二级合作商"),
    /**
     * 操作员
     */
    INNER_STAFF("内部员工"),

    /**
     * 厂商
     */
    MANUFACTURER("厂商");

    private String name;

    OperatorRoleTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean canUpdate(OperatorRoleTypeEnum typeEnum){
        return typeEnum != INNER_STAFF && typeEnum != BUSINESS_MANAGER;
    }

}
