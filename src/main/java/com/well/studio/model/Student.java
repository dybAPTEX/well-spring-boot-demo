package com.well.studio.model;

import com.well.studio.model.base.AbstractBo;
import lombok.Data;

@Data
public class Student extends AbstractBo {
    //    com.well.studio.model.Student
    /**
     * 经纪人唯一标识
     */
    private String uuid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否正常使用状态，0：不，1：正常
     */
    private Integer active;
}
