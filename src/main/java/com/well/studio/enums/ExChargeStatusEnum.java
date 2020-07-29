package com.well.studio.enums;

public enum ExChargeStatusEnum {

    /**
     * 待审批，未提交
     */
    UN_SUBMITTED("未提交"),

    /**
     * 已撤回
     */
    HAVE_WITHDRAW("已撤回"),

    /**
     * 审批中
     */
    IN_APPROVAL("审批中"),

    /**
     * 已审批
     */
    HAVE_APPROVAL("已审批"),

    /**
     * 已退回
     */
    HAVE_APPROVAL_BACK("已退回");

    private String name;

    ExChargeStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean canEdit(ExChargeStatusEnum exChargeStatus) {
        if (exChargeStatus.equals(ExChargeStatusEnum.UN_SUBMITTED) || exChargeStatus.equals(ExChargeStatusEnum.HAVE_WITHDRAW)
                || exChargeStatus.equals(ExChargeStatusEnum.HAVE_APPROVAL_BACK)) {
            return true;
        }
        return false;
    }

    public static boolean couldWitDraw(ExChargeStatusEnum exChargeStatus) {
        switch (exChargeStatus) {
            case IN_APPROVAL:
                return true;
            case UN_SUBMITTED:
            case HAVE_WITHDRAW:
            case HAVE_APPROVAL_BACK:
            case HAVE_APPROVAL:
            default:
                return false;
        }
    }
}
