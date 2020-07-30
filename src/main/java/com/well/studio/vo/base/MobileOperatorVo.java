package com.well.studio.vo.base;

import com.well.studio.enums.ContractStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 移动用户登录操作对象
 */

@Data
public class MobileOperatorVo {
    private static final long serialVersionUID = 1564384941092974508L;

    /**
     * 用户(手机号)
     */
    private String userCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String authCode;

    /**
     * 上次获取验证码的时间
     */
    private Date lastGetAuthCodeTime;

    /**
     * 今日尝试获取验证码次数
     */
    private Integer todayGetAuthCodeTimes;

    /**
     * 登录时间
     */
    private Date signTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次试图登录时间
     */
    private Date lastAttemptedLoginTime;

    /**
     * 验证码错误次数
     */
    private Integer authCodeErrorTimes;

    /**
     * 记住我
     */
    @ApiModelProperty("记住我")
    private Boolean rememberMe;

    private Long productId;

    private String productName;

    private Long contractId;

    private String contractCode;

    /**
     * 登录时合同状态
     */
    private ContractStatusEnum contractOrgStatus;

    private List<Long> accountIdList;

    private Long clientId;

    private String clientName;


}
