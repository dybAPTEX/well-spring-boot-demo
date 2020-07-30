package com.well.studio.util.mybatisUtil;


import com.well.studio.model.base.AbstractBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Sample extends AbstractBo {

    private static final long serialVersionUID = 4512453738481291194L;

    /**
     * 测试编码
     */
    @ApiModelProperty("测试编码")
    @BizColumn(required = false)
    private String testCode;

    /**
     * 测试日期
     */
    @ApiModelProperty("测试日期")
    private Date testTime;

    /**
     * 测试布尔
     */
    @ApiModelProperty("测试布尔")
    private Boolean testBoolean;

    /**
     * 测试长整型
     */
    @ApiModelProperty("测试长整型")
    private Long testLong;

    /**
     * 测试整型
     */
    @ApiModelProperty("测试整型")
    private Integer testInteger;

}
