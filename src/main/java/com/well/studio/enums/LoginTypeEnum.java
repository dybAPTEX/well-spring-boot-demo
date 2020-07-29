package com.well.studio.enums;

/**
 * 登陆类型
 */
public enum LoginTypeEnum {
    /**
     * 页面登陆
     */
    WEB("页面登陆"),
    /**
     * 移动端登陆
     */
    MOBILE("移动端登陆");

    private String name;

    LoginTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
