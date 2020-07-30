package com.well.studio.helper;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: well-spring-boot-demo
 * @description: Bean映射封装
 * @author: daiyunbo
 * @create: 2020-07-30 19:08
 **/
@Component
public class DozerHelper {
    private Mapper mapper;

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    public <P> P clone(P base) {
        if (base == null) {
            return null;
        } else {
            return (P) mapper.map(base, base.getClass());
        }
    }

    public <P> List<P> cloneList(List<P> baseList) {
        if (baseList == null) {
            return null;
        } else {
            List<P> targetList = new ArrayList<>();
            for (P p : baseList) {
                targetList.add(clone(p));
            }
            return targetList;
        }
    }

    public <V, P> P convert(V base, Class<P> target) {
        if (base == null) {
            return null;
        } else {
            return mapper.map(base, target);
        }
    }

    public <V, P> P convert(V base, P target) {
        if (base != null) {
            mapper.map(base, target);
            return target;
        }
        return target;
    }

    public <V, P> List<P> convertList(List<V> baseList, Class<P> target) {
        if (baseList == null) {
            return null;
        } else {
            List<P> targetList = new ArrayList<>();
            for (V vo : baseList) {
                targetList.add(convert(vo, target));
            }
            return targetList;
        }
    }
}
