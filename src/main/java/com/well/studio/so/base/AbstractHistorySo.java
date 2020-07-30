package com.well.studio.so.base;

import com.well.studio.util.mybatisUtil.OperatorTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractHistorySo extends AbstractSo {

    private static final long serialVersionUID = 1993072186482038252L;

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

    /**
     * 默认的页面数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 50;

    /**
     * 当前页面数
     */
    private Integer pageNumber = 1;

    /**
     * 页面数
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
