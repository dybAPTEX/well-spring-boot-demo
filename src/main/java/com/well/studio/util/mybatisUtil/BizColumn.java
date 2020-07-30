package com.well.studio.util.mybatisUtil;

import com.well.studio.enums.ComponentType;

import java.lang.annotation.*;

/**
 * 前端列的annotation
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BizColumn {

    /**
     * 控件类型
     */
    ComponentType componentType() default ComponentType.INPUT;

    /**
     * 模糊查询字段
     */
    boolean fuzzyColumn() default false;

    /**
     * 是否为一个Tab
     */
    boolean isTab() default false;

    /**
     * 是否进行必填校验(后端)
     */
    boolean checkRequired() default false;

    /**
     * 是否必填
     */
    boolean required() default true;

    /**
     * 是否唯一
     */
    boolean unique() default false;
}
