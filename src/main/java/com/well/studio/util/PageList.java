package com.well.studio.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PageList<T extends Serializable>  {
    private List<T> list;

    private int count;

    private Integer size;

    /**
     * 预定义类型1
     */
    private Long udf1;
    /**
     * 预定义类型2
     */
    private String udf2;
    /**
     * 预定义类型3
     */
    private Object udf3;
    /**
     * 预定义类型4
     */
    private List<T> udf4;


    public PageList() {
    }

    public PageList(List<T> list, int count) {
        this.list = list;
        this.count = count;
    }

    public PageList(List<T> list, int count, Integer pageSize) {
        this.list = list;
        this.count = count;
        this.size = pageSize == null ? null
                : new BigDecimal(count).divide(new BigDecimal(pageSize), CommonConstant.ZERO_INT, RoundingMode.UP).intValue();
    }
}
