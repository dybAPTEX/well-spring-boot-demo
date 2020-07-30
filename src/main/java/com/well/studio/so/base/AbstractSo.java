package com.well.studio.so.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础搜索类
 */
@Data
public class AbstractSo implements Serializable {
    private static final long serialVersionUID = 4009650342175211289L;

    /**
     * 默认的页面数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 50;

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
     * 域
     */
    private Long domainId;

    /**
     * 当前页面数
     */
    private Integer pageNumber = 1;

    /**
     * 页面数
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * objectId list
     */
    private List<Long> idList;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    @Override
    public String toString() {
        return "AbstractSo{" +
                "creatorId=" + creatorId +
                ", creator='" + creator + '\'' +
                ", updaterId=" + updaterId +
                ", updater='" + updater + '\'' +
                ", domainId=" + domainId +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", idList=" + idList +
                ", id=" + id +
                '}';
    }
}
