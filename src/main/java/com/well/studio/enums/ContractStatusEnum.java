package com.well.studio.enums;

import com.well.studio.exception.BizException;
import com.google.common.collect.Lists;
import java.util.List;

import static com.well.studio.enums.ExceptionType.VIOLATE_BIZ_RULE;

public enum ContractStatusEnum {
    /**
     * 草稿
     */
    DRAFT("草稿"),

    /**
     * 已生效
     */
    ACTIVE("已生效"),

    /**
     * 已失效
     */
    INACTIVE("已失效"),

    /**
     * 暂停执行
     */
    PAUSE("暂停执行"),

    /**
     * 进行中
     */
    IN_PROGRESS("进行中"),

    /**
     * @deprecated  2019.11.14
     * 已到期
     */
    EXPIRED("已到期"),

    /**
     * 待审核
     */
    PRE_CHECK("待审核"),

    /**
     * 已审核
     */
    CHECKED("已审核"),

    /**
     * 审核拒绝
     */
    CHECK_REFUSE("审核拒绝"),

    /**
     * 已撤回
     */
    HAVE_WITHDRAW("已撤回"),

    /**
     * 失败
     */
    FAIL("失败");

    private String name;

    ContractStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void canActive(ContractStatusEnum contractStatusEnum) {
        switch (contractStatusEnum) {
            case ACTIVE:
                throw new BizException("已生效的不能重复激活", VIOLATE_BIZ_RULE);
            case IN_PROGRESS:
                throw new BizException("进行中合同的不能重复激活", VIOLATE_BIZ_RULE);
            case INACTIVE:
                throw new BizException("已失效的不能激活", VIOLATE_BIZ_RULE);
            case DRAFT:
                throw new BizException("草稿合同不能直接激活", VIOLATE_BIZ_RULE);
            default:
                break;
        }
    }

    public static void canInactive(ContractStatusEnum contractStatusEnum) {
        switch (contractStatusEnum) {
            case INACTIVE:
                throw new BizException("已失效的不能重复失效", VIOLATE_BIZ_RULE);
            case DRAFT:
                break;
            default:
                break;
        }
    }

    public static boolean canSubmit(ContractStatusEnum contractStatusEnum) {
        if (contractStatusEnum == DRAFT) {
            return true;
        }
        return false;
    }

    public static boolean canUpdate(ContractStatusEnum contractStatusEnum) {
        if (contractStatusEnum == DRAFT || contractStatusEnum == CHECK_REFUSE) {
            return true;
        }
        return false;
    }

    public static boolean canPassOrRefuse(ContractStatusEnum contractStatusEnum) {
        return isPreAudit(contractStatusEnum);
    }

    public static boolean canRenew(ContractStatusEnum contractStatusEnum) {
        if (contractStatusEnum == ACTIVE) {
            return true;
        }
        return false;
    }

    public static boolean canRetry(ContractStatusEnum contractStatusEnum) {
        if (contractStatusEnum == FAIL) {
            return true;
        }
        return false;
    }

    public static boolean isInProgress(ContractStatusEnum contractStatusEnum) {
        if (contractStatusEnum == IN_PROGRESS) {
            return true;
        }
        return false;
    }

    /**
     * 可用的合同状态列表
     *
     * @return
     */
    public static List availableStatusList() {
        return Lists.newArrayList(DRAFT.name(), ACTIVE.name(), PAUSE.name(), IN_PROGRESS.name(), PRE_CHECK.name(),
                CHECKED.name(), FAIL.name());
    }

    /**
     * 是否可以发起审批
     *
     * @param statusEnum
     * @return
     */
    public static boolean canStartProcess(ContractStatusEnum statusEnum) {
        return DRAFT == statusEnum || canReStartProcess(statusEnum);
    }

    /**
     * 是否可以重新发起审批
     *
     * @param statusEnum
     * @return
     */
    public static boolean canReStartProcess(ContractStatusEnum statusEnum) {
        return HAVE_WITHDRAW == statusEnum || CHECK_REFUSE == statusEnum;
    }

    /**
     * 是否可以撤回审批
     *
     * @param statusEnum
     * @return
     */
    public static boolean canWithdraw(ContractStatusEnum statusEnum) {
        return isPreAudit(statusEnum);
    }

    /**
     * 是否为待审核状态
     *
     * @param statusEnum
     * @return
     */
    private static boolean isPreAudit(ContractStatusEnum statusEnum) {
        return PRE_CHECK == statusEnum;
    }

    public static ContractStatusEnum getStatusByExChargeStatus(ExChargeStatusEnum statusEnum) {
        if (statusEnum == null) {
            return null;
        }
        switch (statusEnum) {
            case UN_SUBMITTED:
                return null;
            case HAVE_WITHDRAW:
                return HAVE_WITHDRAW;
            case IN_APPROVAL:
                return PRE_CHECK;
            case HAVE_APPROVAL:
                return CHECKED;
            case HAVE_APPROVAL_BACK:
                return CHECK_REFUSE;
            default:
                return null;
        }
    }

    /**
     * 是否需要创建对应的账户
     *
     * @param status
     * @return
     */
    public static boolean needBuildAccount(ContractStatusEnum status) {
        if (DRAFT == status || INACTIVE == status) {
            return false;
        }
        return true;
    }

    /**
     * 在该状态下是否能使用模板
     *
     * @return
     */
    public static boolean canUseTemplate(ContractStatusEnum status) {
        if (status == null) {
            return false;
        }
        switch (status) {
            case DRAFT:
            case PRE_CHECK:
            case CHECK_REFUSE:
                return true;
            default:
                return false;
        }
    }

    /**
     * 可以提交附件复核的状态
     * @param status
     * @return
     */
    public static boolean canSubmitAttachment(ContractStatusEnum status) {
        if (status == null) {
            return false;
        }
        switch (status) {
            case INACTIVE:
            case FAIL:
                return false;
            default:
                return true;
        }

    }
}
