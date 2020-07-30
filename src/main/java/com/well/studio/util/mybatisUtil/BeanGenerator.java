package com.well.studio.util.mybatisUtil;


import com.well.studio.enums.GeneType;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BeanGenerator extends AbstractGenerator {

    public String geneBeanStr(File file, Class clazz, GeneType geneType) {
        File modelFile = FileHelper.getModelFile(clazz);

        if (file.exists() && isBizClass(readFileReturnList(file))) {
            return readFile(file).toString();
        }

        List<String> fileStrList = readFileReturnList(modelFile);

        List<String> replacedStrList = replaceFileList4Vo(clazz, fileStrList, geneType);

        return convert2FileStr(replacedStrList);
    }

    /**
     * 将文件内容集合转化为文件字符串
     */
    private String convert2FileStr(List<String> replacedStrList) {
        StringBuilder beanSb = new StringBuilder();
        for (String replacedStr : replacedStrList) {
            beanSb.append(replacedStr).append("\n");
        }

        return beanSb.toString();
    }

    /**
     * 将文件内容集合中需要替换的字符进行替换
     */
    private List<String> replaceFileList4Vo(Class clazz, List<String> fileStrList, GeneType geneType) {
        List<String> replacedStrList = new ArrayList<>();

        BeanInfo modelInfo;
        BeanInfo replaceInfo;
        switch (geneType) {
            case VO:
                modelInfo = getModelInfo(clazz);
                replaceInfo = getReplaceInfo4Vo(clazz);
                break;
            case SO:
                modelInfo = getModelInfo(clazz);
                replaceInfo = getReplaceInfo4So(clazz);
                break;
            case HISTORY_BO:
                modelInfo = getModelInfo(clazz);
                replaceInfo = getReplaceInfo4HistoryBo(clazz);
                break;
            case HISTORY_VO:
                modelInfo = getModelInfo(clazz);
                replaceInfo = getReplaceInfo4HistoryVo(clazz);
                break;
            case HISTORY_SO:
                modelInfo = getModelInfo(clazz);
                replaceInfo = getReplaceInfo4HistorySo(clazz);
                break;
            default:
                return replacedStrList;
        }

        for (String line : fileStrList) {
            if (line == null) {
                continue;
            }
            if (line.startsWith("package")) {
                line = line.replaceAll(modelInfo.getPackageName(), replaceInfo.getPackageName());
            }
            if (line.startsWith("public class")) {
                line = line.replaceAll(modelInfo.getSimpleName(), replaceInfo.getSimpleName());
                line = line.replaceAll(modelInfo.getExtendsClass(), replaceInfo.getExtendsClass());
            }
            if (line.startsWith("import") && line.contains(modelInfo.getExtendsClassPathName())) {
                line = line.replaceAll(modelInfo.getExtendsClassPathName(), replaceInfo.getExtendsClassPathName());
            }
            replacedStrList.add(line);
        }
        return replacedStrList;
    }

    private BeanInfo getReplaceInfo4So(Class clazz) {
        BeanInfo replaceInfo = new BeanInfo();
        replaceInfo.setPackageName(PathHelper.getSoPackageName(clazz));
        replaceInfo.setSimpleName(clazz.getSimpleName() + "So");
        replaceInfo.setExtendsClassPathName("com.well.studio.so.base.AbstractSo");
        replaceInfo.setExtendsClass("AbstractSo");
        return replaceInfo;
    }

    private BeanInfo getReplaceInfo4HistorySo(Class clazz) {
        BeanInfo replaceInfo = new BeanInfo();
        replaceInfo.setPackageName(PathHelper.getSoPackageName(clazz));
        replaceInfo.setSimpleName(clazz.getSimpleName() + "HistorySo");
        replaceInfo.setExtendsClassPathName("com.well.studio.so.base.AbstractHistorySo");
        replaceInfo.setExtendsClass("AbstractHistorySo");
        return replaceInfo;
    }

    private BeanInfo getReplaceInfo4HistoryVo(Class clazz) {
        BeanInfo replaceInfo = new BeanInfo();
        replaceInfo.setPackageName(PathHelper.getVoPackageName(clazz));
        replaceInfo.setSimpleName(clazz.getSimpleName() + "HistoryVo");
        replaceInfo.setExtendsClassPathName("com.well.studio.vo.base.AbstractHistoryVo");
        replaceInfo.setExtendsClass("AbstractHistoryVo");
        return replaceInfo;
    }

    private BeanInfo getReplaceInfo4Vo(Class clazz) {
        BeanInfo replaceInfo = new BeanInfo();
        replaceInfo.setPackageName(PathHelper.getVoPackageName(clazz));
        replaceInfo.setSimpleName(clazz.getSimpleName() + "Vo");
        replaceInfo.setExtendsClassPathName("com.well.studio.vo.base.AbstractVo");
        replaceInfo.setExtendsClass("AbstractVo");
        return replaceInfo;
    }

    private BeanInfo getReplaceInfo4HistoryBo(Class clazz) {
        BeanInfo replaceInfo = new BeanInfo();
        replaceInfo.setPackageName(PathHelper.getModelPackageName(clazz));
        replaceInfo.setSimpleName(clazz.getSimpleName() + "History");
        replaceInfo.setExtendsClassPathName("com.well.studio.model.base.AbstractHistoryBo");
        replaceInfo.setExtendsClass("AbstractHistoryBo");
        return replaceInfo;
    }

    private BeanInfo getHistoryModelInfo(Class clazz) {
        BeanInfo modelInfo = new BeanInfo();
        modelInfo.setPackageName(PathHelper.getModelPackageName(clazz));
        modelInfo.setSimpleName(clazz.getSimpleName());
        modelInfo.setExtendsClassPathName("com.well.studio.model.base.AbstractHistoryBo");
        modelInfo.setExtendsClass("AbstractHistoryBo");
        return modelInfo;
    }

    private BeanInfo getModelInfo(Class clazz) {
        BeanInfo modelInfo = new BeanInfo();
        modelInfo.setPackageName(PathHelper.getModelPackageName(clazz));
        modelInfo.setSimpleName(clazz.getSimpleName());
        modelInfo.setExtendsClassPathName("com.well.studio.model.base.AbstractBo");
        modelInfo.setExtendsClass("AbstractBo");
        return modelInfo;
    }

    public String geneBeanStr(File tempFile, Class clazz, String packageName) {
        String sbTemp = readFile(tempFile).toString();
        sbTemp = replaceSpecialSymbols4Bo(sbTemp, clazz, packageName);

        List<InnerField> innerFieldList = GenerateCodeHelper.geneInnerFieldList(clazz);

        String columnStr = genColumnStr(innerFieldList);

        sbTemp = sbTemp.replaceAll("%\\{columnStr\\}", columnStr);

        return sbTemp;
    }

    /**
     * 生成
     */
    private static String genColumnStr(List<InnerField> innerFieldList) {
        StringBuilder sb = new StringBuilder();

        for (InnerField innerField : innerFieldList) {
            if (!innerField.getDeclaringClass().equals(BASE_MODEL_NAME)) {
                sb.append("\tprivate ").append(innerField.getTypeClassName()).append(" ").append(innerField.getCamelCaseColumnName())
                        .append(";\n\n");
            }
        }

        return sb.toString();
    }

}
