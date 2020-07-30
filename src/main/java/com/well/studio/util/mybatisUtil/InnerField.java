package com.well.studio.util.mybatisUtil;

import com.well.studio.enums.ComponentType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InnerField {
    /**
     * 类名
     * exp.Sample
     */
    String declaringClass;

    String className;

    /**
     * 驼峰列名
     * exp.testCode
     */
    String camelCaseColumnName;

    /**
     * 下划线列名
     * exp.test_code
     */
    String underlineColumnName;

    /**
     * 类型全称
     * exp.java.lang.String
     */
    String typeFullName;

    /**
     * 类型
     * exp.String
     */
    String typeClassName;

    /**
     * 备注
     * exp.测试编码
     */
    String comment;

    /**
     * 注释
     */
    List<String> annotationList;

    /**
     * 泛型类型
     */
    String genericType;

    public String getGenericType() {
        return genericType;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(String declaringClass) {
        this.declaringClass = declaringClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCamelCaseColumnName() {
        return camelCaseColumnName;
    }

    public void setCamelCaseColumnName(String camelCaseColumnName) {
        this.camelCaseColumnName = camelCaseColumnName;
    }

    public String getUnderlineColumnName() {
        return underlineColumnName;
    }

    public void setUnderlineColumnName(String underlineColumnName) {
        this.underlineColumnName = underlineColumnName;
    }

    public String getTypeFullName() {
        return typeFullName;
    }

    public void setTypeFullName(String typeFullName) {
        this.typeFullName = typeFullName;
    }

    public String getTypeClassName() {
        return typeClassName;
    }

    public void setTypeClassName(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<String> annotationList) {
        this.annotationList = annotationList;
    }

    public String getTotalName() {
        if (getClassName() != null && getCamelCaseColumnName() != null) {
            return getClassName() + "." + getCamelCaseColumnName();
        } else {
            return getCamelCaseColumnName();
        }
    }

    /**
     * 获取FrontColumn注解的字符串
     *
     * @return FrontColumn注解的字符串
     */
    public String getFrontColumnAnnotation() {
        if (CollectionUtils.isNotEmpty(this.getAnnotationList())) {
            for (String annotation : this.getAnnotationList()) {
                if (annotation.contains("@BizColumn")) {
                    return annotation;
                }
            }
        }
        return null;
    }

    public boolean isFuzzyColumn() {
        String annotation = getAnnotationByName("fuzzyColumn");
        return annotation != null && Boolean.TRUE.toString().equalsIgnoreCase(annotation);
    }

    private String getAnnotationByName(String name) {
        Map<String, String> annotationFieldMap = geneAnnotationFieldMap();
        return annotationFieldMap.get(name);
    }

    public boolean isTabColumn() {
        String annotation = getAnnotationByName("isTab");
        return annotation != null && Boolean.TRUE.toString().equalsIgnoreCase(annotation);
    }

    public boolean isCheckRequired() {
        String annotation = getAnnotationByName("checkRequired");
        return annotation != null && Boolean.TRUE.toString().equalsIgnoreCase(annotation);
    }

    public boolean isRequired() {
        String annotation = getAnnotationByName("required");
        return !(annotation != null && Boolean.FALSE.toString().equalsIgnoreCase(annotation));
    }

    public boolean isUnique() {
        String annotation = getAnnotationByName("unique");
        return annotation != null && Boolean.TRUE.toString().equalsIgnoreCase(annotation);
    }

    public ComponentType getComponentType() {
        String annotation = getAnnotationByName("componentType");
        return ComponentType.convert2ComponentType(annotation);
    }

    private Map<String, String> geneAnnotationFieldMap() {
        String frontColumnAnnotation = getFrontColumnAnnotation();
        if (StringUtils.isEmpty(frontColumnAnnotation)) {
            return new HashMap<>();
        }
        return AnnotationUtil.getAnnotationFieldMap(frontColumnAnnotation);
    }

}
