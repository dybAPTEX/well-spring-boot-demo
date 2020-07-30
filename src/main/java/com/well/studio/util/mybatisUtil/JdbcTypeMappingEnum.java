package com.well.studio.util.mybatisUtil;

public enum JdbcTypeMappingEnum {
    /**
     * 长整型
     */
    LONG("NUMERIC", "Long", "java.lang.Long"),
    /**
     * 整型
     */
    INTEGER("NUMERIC", "Integer", "java.lang.Integer"),
    /**
     * 字符串
     */
    STRING("VARCHAR", "String", "java.lang.String"),
    /**
     * BigDecimal
     */
    BIG_DECIMAL("NUMERIC", "BigDecimal", "java.math.BigDecimal"),
    /**
     * 布尔型
     */
    BOOLEAN("NUMERIC", "Boolean", "java.lang.Boolean"),
    /**
     * 日期
     */
    DATE("TIMESTAMP", "Date", "java.util.Date"),
    /**
     * 浮点型
     */
    FLOAT("NUMERIC", "Float", "java.lang.Float"),
    /**
     * 长浮点型
     */
    DOUBLE("NUMERIC", "Double", "java.lang.Double");

    /**
     * 对应数据库的数据类型
     */
    private String jdbcType;

    /**
     * Java类
     */
    private String typeClassName;

    /**
     * Java类全路径
     */
    private String typeFullName;

    JdbcTypeMappingEnum(String jdbcType, String typeClassName, String typeFullName) {
        this.jdbcType = jdbcType;
        this.typeClassName = typeClassName;
        this.typeFullName = typeFullName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public String getTypeClassName() {
        return typeClassName;
    }

    public String getTypeFullName() {
        return typeFullName;
    }

    /**
     * 根据Java类全路径获取JdbcTypeMappingEnum
     *
     * @param typeFullName Java类全路径
     * @return JdbcTypeMappingEnum
     */
    public static JdbcTypeMappingEnum getJdbcTypeMappingEnumByTypeFullName(String typeFullName) {
        for (JdbcTypeMappingEnum jdbcTypeMappingEnum : JdbcTypeMappingEnum.values()) {
            if (jdbcTypeMappingEnum.getTypeFullName().equals(typeFullName)) {
                return jdbcTypeMappingEnum;
            }
        }

        return null;
    }

    /**
     * 根据Java类获取JdbcTypeMappingEnum
     *
     * @param typeClassName Java类
     * @return JdbcTypeMappingEnum
     */
    public static JdbcTypeMappingEnum getJdbcTypeMappingEnumByTypeClassName(String typeClassName) {
        for (JdbcTypeMappingEnum jdbcTypeMappingEnum : JdbcTypeMappingEnum.values()) {
            if (jdbcTypeMappingEnum.getTypeClassName().equals(typeClassName)) {
                return jdbcTypeMappingEnum;
            }
        }

        return null;
    }

}