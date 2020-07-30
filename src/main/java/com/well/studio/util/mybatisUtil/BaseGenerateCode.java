package com.well.studio.util.mybatisUtil;

import com.well.studio.util.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseGenerateCode {

    protected static List<Class> loadClassList(String str0) throws ClassNotFoundException {
        List<Class> clazzList = new ArrayList<>();
        if (str0.contains(CommonConstant.SEMICOLON)) {
            String[] clazzNameArray = str0.split(CommonConstant.SEMICOLON);
            for (String clazzName : clazzNameArray) {
                if (StringUtils.isEmpty(clazzName)) {
                    continue;
                }
                clazzList.add(Class.forName(clazzName));
            }
        } else {
            clazzList.add(Class.forName(str0));
        }
        return clazzList;
    }

}