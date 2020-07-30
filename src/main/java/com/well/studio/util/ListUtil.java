package com.well.studio.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List帮助类
 *
 */
public final class ListUtil {

    private ListUtil() {

    }

    public static List<Long> string2LongList(List<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }
        return stringList.stream().map(stringItem -> Long.parseLong(stringItem.trim())).collect(Collectors.toList());
    }

    public static List<String> long2StringList(List<Long> longList) {
        if (CollectionUtils.isEmpty(longList)) {
            return null;
        }
        return longList.stream().map(Object::toString).collect(Collectors.toList());
    }

    public static boolean ifCollectionOnlyOneSize(Collection collection) {
        return collection != null && collection.size() == 1;
    }
}
