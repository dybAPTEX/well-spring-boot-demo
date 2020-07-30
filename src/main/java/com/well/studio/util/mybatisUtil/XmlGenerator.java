package com.well.studio.util.mybatisUtil;

import java.io.File;
import java.util.List;

public class XmlGenerator extends AbstractGenerator {

    private static final String BASE_MODEL_NAME = "AbstractBo";

    public String geneBeanStr(File tempFile, Class clazz, String packageName) {
        String sbTemp = readFile(tempFile).toString();
        sbTemp = replaceSpecialSymbols4Bo(sbTemp, clazz, packageName);

        String columnStr = genColumnStr(clazz);

        sbTemp = sbTemp.replaceAll("%\\{columnStr\\}", columnStr);

        return sbTemp;
    }

    /**
     * 生成
     */
    private static String genColumnStr(Class clazz) {
        StringBuilder sb = new StringBuilder();

        List<InnerField> innerFieldList = GenerateCodeHelper.geneInnerFieldWithComment(clazz);
        for (InnerField innerField : innerFieldList) {
            if (!innerField.getDeclaringClass().equals(BASE_MODEL_NAME)) {
                sb.append("\t\t\t<column property=\"").append(innerField.getCamelCaseColumnName())
                        .append("\" dataType=\"string\" excelTitleName=\"").append(innerField.getComment()).append("\" />\n");
            }
        }

        return sb.toString();
    }

}
