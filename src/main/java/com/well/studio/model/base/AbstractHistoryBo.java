package com.well.studio.model.base;

import com.well.studio.util.mybatisUtil.OperatorTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: well-spring-boot-demo
 * @description: 基础pojo操作记录父类，可更具项目更改
 * @author: daiyunbo
 * @create: 2020-07-30 19:08
 **/
@Data
public abstract class AbstractHistoryBo implements Serializable {

    private static final long serialVersionUID = 1993072186482038252L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 关联Id
     */
    private Long refId;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作类型
     */
    private OperatorTypeEnum operatorType;

    /**
     * 乐观锁
     */
    private Integer lockVersion = 0;

    /**
     * 域
     */
    private Long domainId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}