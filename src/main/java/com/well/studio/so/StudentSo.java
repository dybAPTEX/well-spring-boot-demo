package com.well.studio.so;

import com.well.studio.so.base.AbstractSo;
import lombok.Data;

@Data
public class StudentSo extends AbstractSo {
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
