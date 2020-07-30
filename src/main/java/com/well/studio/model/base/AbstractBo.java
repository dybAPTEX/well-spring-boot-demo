package com.well.studio.model.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: well-spring-boot-demo
 * @description: 基础pojo父类，可更具项目更改
 * @author: daiyunbo
 * @create: 2020-07-30 19:08
 **/
@Data
public class AbstractBo implements Serializable {
    /**
     * 编号
     */
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
     * 域
     */
    private Long domainId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
