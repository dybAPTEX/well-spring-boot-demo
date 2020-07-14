package com.well.studio.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @author zhaojie
 * @date 2020/07/14 14:49:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {


    /**
     * 自增id
     */
    private Integer id;

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
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;

}