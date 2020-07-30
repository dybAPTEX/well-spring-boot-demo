package com.well.studio.util;

import com.well.studio.vo.base.OperatorVo;
import lombok.Data;

@Data
public class SystemContext {
    /**
     * 系统操作者
     */
    private OperatorVo operator;

    /**
     * 访问ip
     */
    private String ip;

    /**
     * sessionId
     */
    private String sessionId;

    public SystemContext() {
    }

    public SystemContext(OperatorVo operator) {
        this.operator = operator;
    }
}
