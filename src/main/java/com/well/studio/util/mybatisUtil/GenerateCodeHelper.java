package com.well.studio.util.mybatisUtil;

import com.well.studio.model.base.AbstractBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
    public final class GenerateCodeHelper {

    private static final String TABLE_PRE = "bp_";

    private static final String HISTORY = "HISTORY";

    private static final Integer MAX_TABLE_NAME_LENGTH = 26;

    private static final char CAPITAL_ASCII_BEGIN = 65;

    private static final char CAPITAL_ASCII_END = 90;

    private static final Logger LOG = LoggerFactory.getLogger(GenerateCodeHelper.class);

    private GenerateCodeHelper() {
    }

    public static List<InnerField> geneInnerFieldList(Class clazz) {
        List<Field> fieldList = FieldUtil.getParentAndSelfFields(clazz);
        List<InnerField> innerFieldList = new ArrayList<>();
        for (Field field : fieldList) {
            innerFieldList.addAll(getAllFields(field, field.getType(), field.getName()));
        }
        fillAnnotation(clazz, innerFieldList);
        return innerFieldList;
    }

    public static List<InnerField> geneSelfFieldList(Class clazz) {
        List<Field> fieldList = FieldUtil.getSelfFields(clazz);
        List<InnerField> innerFieldList = new ArrayList<>();
        for (Field field : fieldList) {

            List<InnerField> ret = new ArrayList<>();
            if (field != null) {
                String className = null;
                if (FieldUtil.isBaseDataType(clazz)) {
                    className = null;
                }
                InnerField retField =
                        buildInnerField(field, clazz, className, field.getDeclaringClass().getSimpleName());
                ret.add(retField);
            }

            innerFieldList.addAll(ret);
        }
        fillAnnotation(clazz, innerFieldList);
        return innerFieldList;
    }

    public static List<InnerField> geneHistoryInnerFieldList(Class clazz) {
        List<Field> fieldList = FieldUtil.getParentAndSelfFields(clazz);
        Class historyBaseClazz = null;
        try {
            historyBaseClazz = Class.forName("com.well.studio.model.base.AbstractHistoryBo");
        } catch (ClassNotFoundException e) {
            log.info("--AbstractHistoryBo不存在");
        }

        List<Field> historyFieldList = FieldUtil.getParentAndSelfFields(historyBaseClazz);

        List<InnerField> innerFieldList = new ArrayList<>();
        for (Field historyField : historyFieldList) {
            innerFieldList.addAll(getAllFields(historyField, historyField.getType(), historyField.getName()));
        }

        for (Field field : fieldList) {
            if (field.getDeclaringClass().isAssignableFrom(AbstractBo.class)) {
                continue;
            }
            innerFieldList.addAll(getAllFields(field, field.getType(), field.getName()));
        }

        fillAnnotation(clazz, innerFieldList);
        return innerFieldList;
    }

    public static List<InnerField> geneHistoryInnerFieldWithComment(Class clazz) {
        List<InnerField> innerFieldList = geneHistoryInnerFieldList(clazz);

        fillComment(clazz, innerFieldList);

        return innerFieldList;
    }

    public static List<InnerField> geneInnerFieldWithComment(Class clazz) {
        List<InnerField> innerFieldList = geneInnerFieldList(clazz);

        fillComment(clazz, innerFieldList);

        return innerFieldList;
    }

    public static List<InnerField> geneInnerFieldWithCommentAndAnnotation(Class clazz) {
        List<InnerField> innerFieldList = geneInnerFieldList(clazz);

        fillComment(clazz, innerFieldList);

        fillAnnotation(clazz, innerFieldList);

        return innerFieldList;
    }

    public static List<InnerField> geneSelfFieldWithCommentAndAnnotation(Class clazz) {
        List<InnerField> innerFieldList = geneSelfFieldList(clazz);

        fillComment(clazz, innerFieldList);

        fillAnnotation(clazz, innerFieldList);

        return innerFieldList;
    }

    private static void fillComment(Class clazz, List<InnerField> innerFieldList) {
        Map<String, String> column2CommentMap = readAllCommentFromModelFile(clazz);
        Map<String, String> defaultColumn2CommentMap = getDefaultColumn2CommentMap();

        for (InnerField field : innerFieldList) {
            String comment = column2CommentMap.get(field.getCamelCaseColumnName());
            if (comment == null) {
                comment = defaultColumn2CommentMap.get(field.getCamelCaseColumnName());
            }
            if (comment != null) {
                field.setComment(comment);
            }
        }
    }

    private static void fillAnnotation(Class clazz, List<InnerField> innerFieldList) {
        Map<String, List<String>> column2AnnotationListMap = readAnnotationFromModelFile(clazz);

        for (InnerField field : innerFieldList) {
            List<String> annotationList = column2AnnotationListMap.get(field.getCamelCaseColumnName());
            field.setAnnotationList(annotationList);
        }
    }

    public static String geneUpperCaseTableName(Class clazz) {
        String lowerCaseTableName = geneTableName(clazz.getSimpleName());
        return lowerCaseTableName.toUpperCase();
    }

    public static String geneTableName(String simpleName) {
        String[] tempArray = simpleName.split("");
        StringBuilder sb = new StringBuilder();
        for (String temp : tempArray) {
            String trimTemp = StringUtils.trim(temp);
            if (!StringUtils.isEmpty(trimTemp)) {
                if (StringUtils.isAllUpperCase(trimTemp)) {
                    sb.append("_");
                    sb.append(trimTemp.toLowerCase());
                } else {
                    sb.append(trimTemp);
                }
            }
        }
        String ret = sb.toString();
        ret = StringUtils.removeStart(ret, "_");
        ret = TABLE_PRE + ret;
        return ret;
    }

    public static String geneSeqName(Class clazz) {
        String tableName = geneTableName(clazz.getSimpleName());
        if (tableName.length() > MAX_TABLE_NAME_LENGTH) {
            String preToken = tableName.substring(0, tableName.lastIndexOf("_"));
            String lastToken = tableName.substring(tableName.lastIndexOf("_"));
            if (lastToken.toUpperCase().equals(HISTORY)) {
                tableName = preToken + "ht";
            }
        }
        return tableName + "_seq";
    }

    public static String geneSeqName(String tableName) {
        if (tableName.length() > MAX_TABLE_NAME_LENGTH) {
            String preToken = tableName.substring(0, tableName.lastIndexOf("_"));
            String lastToken = tableName.substring(tableName.lastIndexOf("_") + 1);
            if (lastToken.toUpperCase().equals(HISTORY)) {
                tableName = preToken + "_ht";
            }
        }
        return tableName + "_seq";
    }

    /**
     * 得到所有字段 非简单类型获取其引用类型字段
     */
    private static List<InnerField> getAllFields(Field inputField, Class clazz, String className) {
        Field thisField = inputField;
        List<InnerField> ret = new ArrayList<>();
        while (thisField != null && !FieldUtil.isBaseDataType(thisField.getType())) {
            className = buildClassName(className, thisField.getName());
            List<Field> allFields = FieldUtil.getParentAndSelfFields(thisField.getType());
            for (Field field : allFields) {
                ret.addAll(getAllFields(field, thisField.getType(), className));
            }
            thisField = null;
        }
        if (thisField != null) {
            if (FieldUtil.isBaseDataType(clazz)) {
                className = null;
            }
            InnerField retField =
                    buildInnerField(thisField, clazz, className, thisField.getDeclaringClass().getSimpleName());
            ret.add(retField);
        }
        return ret;
    }

    private static InnerField buildInnerField(Field field, Class clazz, String className, String declaringClass) {
        InnerField retField = new InnerField();
        retField.setDeclaringClass(declaringClass);
        retField.setClassName(className);
        Class<?> type = field.getType();

        if (type.isAssignableFrom(List.class)) {
            Type fc = field.getGenericType();

            if (fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) fc;
                Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                retField.setGenericType(genericClazz.getSimpleName());
            }
        }

        retField.setCamelCaseColumnName(field.getName());
        retField.setUnderlineColumnName(convertColumnName(field.getName()));
        retField.setTypeFullName(field.getType().getName());
        retField.setTypeClassName(clazz.getSimpleName());
        return retField;
    }

    private static String convertColumnName(String name) {
        name = name.trim();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {

            if (CAPITAL_ASCII_BEGIN <= name.charAt(i) && name.charAt(i) <= CAPITAL_ASCII_END) {
                stringBuffer.append("_");
            }
            stringBuffer.append(name.charAt(i));
        }
        return stringBuffer.toString().toLowerCase();
    }

    private static String buildClassName(String className, String subClassName) {
        if (subClassName.equals(className)) {
            return className;
        } else {
            className = className + "." + subClassName;
        }
        return className;
    }

    private static Map<String, String> getDefaultColumn2CommentMap() {
        Map<String, String> defaultColumn2CommentMap = new HashMap<>();
        defaultColumn2CommentMap.put("id", "编号");
        defaultColumn2CommentMap.put("createdTime", "创建时间");
        defaultColumn2CommentMap.put("updatedTime", "更新时间");
        defaultColumn2CommentMap.put("creatorId", "创建人id");
        defaultColumn2CommentMap.put("creator", "创建人");
        defaultColumn2CommentMap.put("updaterId", "更新人id");
        defaultColumn2CommentMap.put("updater", "更新人");
        defaultColumn2CommentMap.put("lockVersion", "乐观锁");
        defaultColumn2CommentMap.put("domainId", "域");

        defaultColumn2CommentMap.put("refId", "关联Id");
        defaultColumn2CommentMap.put("operationTime", "操作时间");
        defaultColumn2CommentMap.put("operator", "操作人");
        defaultColumn2CommentMap.put("operatorId", "操作人ID");
        defaultColumn2CommentMap.put("operatorType", "操作类型");

        return defaultColumn2CommentMap;
    }

    public static String readHistoryTableCommentFromFile(Class clazz) {
        return readTableCommentFromFile(clazz) + "历史";
    }

    public static String readTableCommentFromFile(Class clazz) {
        File modelFile = FileHelper.getModelFile(clazz);
        BufferedReader br = null;
        String preLine = null;
        try {
            br = new BufferedReader(new FileReader(modelFile));
            String line = br.readLine();
            while (line != null) {
                String trimLine = line.trim();
                if (trimLine.startsWith("*") && !trimLine.endsWith("/") && !"*".equals(trimLine)) {
                    preLine = trimLine;
                }
                if (trimLine.startsWith("@EqualsAndHashCode")) {
                    return preLine != null ? preLine.substring(1).trim() : null;
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error("异常", e);
                }
            }
        }

        return null;
    }

    /**
     * 从文件中读取注解
     */
    private static Map<String, List<String>> readAnnotationFromModelFile(Class clazz) {
        File modelFile = FileHelper.getModelFile(clazz);
        Map<String, List<String>> column2AnnotationListMap = new HashMap<>();

        BufferedReader br = null;
        List<String> preLineList = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(modelFile));
            String line = br.readLine();
            while (line != null) {
                String trimLine = line.trim();

                if (trimLine.contains("@")) {
                    preLineList.add(trimLine);
                }
                if (StringUtils.isEmpty(trimLine)) {
                    preLineList = new ArrayList<>();
                }

                if (trimLine.startsWith("private") && trimLine.contains(";")) {
                    String column = trimLine.substring(trimLine.lastIndexOf(" ") + 1, trimLine.indexOf(";"));
                    column2AnnotationListMap.put(column, preLineList);
                    preLineList = new ArrayList<>();
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error("异常", e);
                }
            }
        }

        return column2AnnotationListMap;
    }

    private static Map<String, String> readAllCommentFromModelFile(Class clazz) {
        Map<String, String> column2CommentMap = readCommentFromModelFile(clazz);
        Class superclass = clazz.getSuperclass();
        if (superclass != null && !Object.class.equals(superclass)) {
            Map<String, String> column2SuperCommentMap = readCommentFromModelFile(superclass);
            for (Map.Entry<String, String> entry : column2SuperCommentMap.entrySet()) {
                column2CommentMap.put(entry.getKey(), entry.getValue());
            }
        }

        return column2CommentMap;
    }

    /**
     * 从文件中读取备注
     */
    private static Map<String, String> readCommentFromModelFile(Class clazz) {
        File modelFile = FileHelper.getModelFile(clazz);
        Map<String, String> column2CommentMap = new HashMap<>();
        BufferedReader br = null;
        String preLine = null;
        try {
            br = new BufferedReader(new FileReader(modelFile));
            String line = br.readLine();
            while (line != null) {
                String trimLine = line.trim();
                if (trimLine.startsWith("*") && !trimLine.endsWith("/") && !"*".equals(trimLine)) {
                    preLine = trimLine;
                }
                if (trimLine.startsWith("private") && trimLine.contains(";")) {
                    String column = trimLine.substring(trimLine.lastIndexOf(" ") + 1, trimLine.indexOf(";"));
                    String comment = preLine != null ? preLine.substring(1).trim() : null;
                    column2CommentMap.put(column, comment);
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error("异常", e);
                }
            }
        }

        return column2CommentMap;
    }

    /**
     * 从文件中读取注解
     */
    public static List<String> readAnnotationFromModelFile(Class clazz, String annotationName) {
        File modelFile = FileHelper.getModelFile(clazz);
        List<String> annotationLineList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(modelFile));
            String line = br.readLine();
            while (line != null) {
                String trimLine = line.trim();
                if (trimLine.contains("@")) {
                    if (annotationName == null || trimLine.contains(annotationName)) {
                        annotationLineList.add(trimLine);
                    }
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error("异常", e);
                }
            }
        }

        return annotationLineList;
    }

    /**
     * 获取所在模块名称
     */
    public static String geneModuleName(Class clazz) {
        Map<String, String> field2ValueMap = getFrontInfoField2ValueMap(clazz);
        return field2ValueMap.get("moduleName");
    }

    /**
     * 获取所在模块名称
     */
    public static String genePageNameCn(Class clazz) {
        Map<String, String> field2ValueMap = getFrontInfoField2ValueMap(clazz);
        return field2ValueMap.get("pageNameCn");
    }

    /**
     * 获取FrontInfo标签下的参数Map
     */
    private static Map<String, String> getFrontInfoField2ValueMap(Class clazz) {
        List<String> annotationLineList = readAnnotationFromModelFile(clazz, "FrontInfo");
        if (CollectionUtils.isNotEmpty(annotationLineList)) {
            String frontInfoAnnotation = annotationLineList.get(0);
            return AnnotationUtil.getAnnotationFieldMap(frontInfoAnnotation);
        }
        return new HashMap<>();
    }

}
