package com.well.studio.so;

import com.well.studio.so.base.AbstractSo;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 * @author zhaojie
 * @date 2020/07/14 14:49:02
 */
@Data
public class UserSo extends AbstractSo {
//    com.well.studio.model.User
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

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 删除时间
     */
    private Date deletedAt;

}
