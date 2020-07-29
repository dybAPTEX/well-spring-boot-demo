package com.well.studio.enums;

import org.apache.commons.lang3.StringUtils;

public enum ComponentType {

    /**
     * 文本框
     */
    INPUT("文本框"),
    /**
     * 文本域
     */
    TEXT_AREA("文本域"),
    /**
     * 时间控件
     */
    DATE("时间控件"),
    /**
     * 时间范围控件
     */
    DATE_RANGE("时间范围控件"),
    /**
     * 下拉框控件
     */
    SELECT("下拉框控件");

    private String name;

    ComponentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ComponentType convert2ComponentType(String componentTypeStr) {
        if (StringUtils.isEmpty(componentTypeStr)) {
            return null;
        }
        switch (componentTypeStr) {
            case "ComponentType.INPUT":
                return ComponentType.INPUT;
            case "ComponentType.TEXT_AREA":
                return ComponentType.TEXT_AREA;
            case "ComponentType.DATE":
                return ComponentType.DATE;
            case "ComponentType.DATE_RANGE":
                return ComponentType.DATE_RANGE;
            case "ComponentType.SELECT":
                return ComponentType.SELECT;
            default:
                return null;
        }
    }
}
