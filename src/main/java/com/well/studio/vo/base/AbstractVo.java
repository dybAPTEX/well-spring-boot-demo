package com.well.studio.vo.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础VO提供创建更新人，时间，域，返回值
 */
@Data
public class AbstractVo implements Serializable {
    private static final long serialVersionUID = 7507237342343039394L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新人id
     */
    private Long updaterId;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 乐观锁
     */
    private Integer lockVersion;

    /**
     * domain
     */
    private Long domainId;

    /**
     * 返回值1
     */
    private String udf1;

    /**
     * 返回值2
     */
    private String udf2;

    /**
     * 返回值3
     */
    private String udf3;

    /**
     * 返回值4
     */
    private String udf4;

    /**
     * 返回值5
     */
    private String udf5;

    /**
     * 返回值6
     */
    private String udf6;

    @Override
    public String toString() {
        return "AbstractVo{" +
                "id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", creatorId=" + creatorId +
                ", creator='" + creator + '\'' +
                ", updaterId=" + updaterId +
                ", updater='" + updater + '\'' +
                ", lockVersion=" + lockVersion +
                ", domainId=" + domainId +
                ", udf1='" + udf1 + '\'' +
                ", udf2='" + udf2 + '\'' +
                ", udf3='" + udf3 + '\'' +
                ", udf4='" + udf4 + '\'' +
                ", udf5='" + udf5 + '\'' +
                ", udf6='" + udf6 + '\'' +
                '}';
    }
}
