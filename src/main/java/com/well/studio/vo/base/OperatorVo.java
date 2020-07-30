package com.well.studio.vo.base;

import com.well.studio.enums.CommonActiveStatusEnum;
import com.well.studio.enums.LoginTypeEnum;
import com.well.studio.enums.OperatorRoleTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class OperatorVo extends AbstractVo {
    private static final long serialVersionUID = -6925622707395780759L;

    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    private String loginName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String nameCn;

    /**
     * 用户名英文
     */
    @ApiModelProperty("用户名英文")
    private String nameEn;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 电话号码
     */
    @ApiModelProperty("phoneCode")
    private String phoneCode;

    /**
     * 客户id
     */
    @ApiModelProperty("客户id")
    private Long clientId;

    /**
     * 用户状态
     */
    @ApiModelProperty("用户状态")
    private CommonActiveStatusEnum status;

    /**
     * 网络权限 （内网 vpn 外网）（111）全权限
     */
    @ApiModelProperty("网络权限")
    private String netAuthority;

    /**
     * ldap用户
     */
    @ApiModelProperty("ldap用户")
    private Boolean ldap;

    /**
     * 盐
     */
    @ApiModelProperty("盐")
    private String salt;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    private Date signTime;

    /**
     * 上次登录时间
     */
    @ApiModelProperty("上次登录时间")
    private Date lastLoginTime;

    /**
     * 上次试图登录时间
     */
    @ApiModelProperty("上次试图登录时间")
    private Date lastAttemptedLoginTime;

    /**
     * 登录错误次数
     */
    @ApiModelProperty("登录错误次数")
    private Integer passwordErrorTimes;

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private OperatorRoleTypeEnum sysOpType;

    /**
     * 用户关联角色集合
     */
    @ApiModelProperty("用户类型")
    private List<Long> roleIdList;

    /**
     * 记住我
     */
    @ApiModelProperty("记住我")
    private Boolean rememberMe;

    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String kaptcha;

    /**
     * 用户关联业务Id
     */
    @ApiModelProperty("用户关联业务Id")
    private List<Long> bizServiceIdList;

    /**
     * 登陆类型
     */
    @ApiModelProperty("登陆类型")
    private LoginTypeEnum loginType;

    /**
     * 用户业务关联渠道
     */
    @ApiModelProperty("用户业务关联渠道")
    private List<String> channelList;

    /**
     * 站点id
     */
    private String siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点编码
     */
    private String siteCode;

    private String customerType;
}
