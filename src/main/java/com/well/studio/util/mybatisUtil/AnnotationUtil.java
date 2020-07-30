package com.well.studio.util.mybatisUtil;

import java.util.HashMap;
import java.util.Map;

public class AnnotationUtil {

    /**
     * 获取注解信息Mapper
     */
    public static Map<String, String> getAnnotationFieldMap(String annotationStr) {
        Map<String, String> field2ValueMap = new HashMap<>();
        if (annotationStr == null) {
            return field2ValueMap;
        }
        annotationStr = annotationStr.trim();
        String body = annotationStr.substring(annotationStr.indexOf("(") + 1, annotationStr.lastIndexOf(")"));
        String[] fieldArray = body.split(",");
        for (String fieldToken : fieldArray) {
            String[] fieldTokenArray = fieldToken.split("=");
            String fieldName = fieldTokenArray[0].trim();
            String fieldValue = fieldTokenArray[1].trim();
            if (fieldValue.contains("\"")) {
                fieldValue = fieldValue.substring(fieldValue.indexOf("\"") + 1, fieldValue.lastIndexOf("\"")).trim();
            }
            field2ValueMap.put(fieldName, fieldValue);
        }

        return field2ValueMap;
    }

}
