package com.well.studio.vo;

import com.well.studio.vo.base.AbstractVo;
import lombok.Data;

@Data
public class StudentVo extends AbstractVo {
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
