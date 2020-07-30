package com.well.studio.util.mybatisUtil;

import lombok.Data;

@Data
public class BeanInfo {
    private String packageName;

    private String simpleName;

    private String extendsClassPathName;

    private String extendsClass;
}
