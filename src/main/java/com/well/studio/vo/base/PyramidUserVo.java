package com.well.studio.vo.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PyramidUserVo  extends AbstractVo {

    private static final long serialVersionUID = -6925622702394780759L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点编码
     */
    private String siteCode;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 来源 EXPRESS-快递 TRANSPORTATION-快运
     */
    private String  customerType;
}
