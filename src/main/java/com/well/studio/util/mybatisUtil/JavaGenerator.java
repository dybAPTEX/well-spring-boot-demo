package com.well.studio.util.mybatisUtil;

import com.well.studio.util.CommonConstant;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

public class JavaGenerator extends AbstractGenerator {

    private static final String SERVICE_IMPL_END = "ServiceImpl.java";
    private static final String DAO_IMPL_END = "DaoImpl.java";
    private static final String DAO_END = "Dao.java";
    private static final String DATE_TYPE = "Date";

    public String geneJavaStr(File file, File tempFile, Class clazz, String packageName, boolean history) {
        String sbTemp = readFile(tempFile).toString();
        sbTemp = replaceAdditional(file, clazz, sbTemp);
        if (history) {
            sbTemp = replaceHistorySpecialSymbols(sbTemp, clazz, packageName);
        } else {
            sbTemp = replaceSpecialSymbols4Bo(sbTemp, clazz, packageName);
        }

        if (file.exists()) {

            List<String> fileStrList = readFileReturnList(file);
            if (isBizClass(fileStrList)) {
                return readFile(file).toString();
            }

            StringBuilder businessStr = geneBusinessStr(fileStrList);

            sbTemp = sbTemp.replace("${Business}", businessStr.toString());
        } else {
            sbTemp = sbTemp.replace("${Business}", "");
        }

        return sbTemp;
    }

    private String replaceAdditional(File file, Class clazz, String sbTemp) {
        List<InnerField> innerFieldList = GenerateCodeHelper.geneInnerFieldWithCommentAndAnnotation(clazz);
        if (file.getName().endsWith(SERVICE_IMPL_END)) {
            sbTemp = sbTemp.replace("${RequireDetail}", geneRequireDetailSb(innerFieldList));
            sbTemp = sbTemp.replace("${CreateDuplicateCheck}", geneCreateDuplicateCheckSb(innerFieldList));
            sbTemp = sbTemp.replace("${CreateDuplicateCheckDetail}", geneCreateDuplicateCheckDetailSb(innerFieldList));
            sbTemp = sbTemp.replace("${UpdateDuplicateCheck}", geneUpdateDuplicateCheckSb(innerFieldList));
            sbTemp = sbTemp.replace("${UpdateDuplicateCheckDetail}", geneUpdateDuplicateCheckDetailSb(innerFieldList));
            sbTemp = sbTemp.replace("${AdditionServiceImplImport}", geneAdditionServiceImplImportSb(clazz));
        } else {
            sbTemp = sbTemp.replace("${RequireDetail}", "");
            sbTemp = sbTemp.replace("${CreateDuplicateCheck}", "");
            sbTemp = sbTemp.replace("${CreateDuplicateCheckDetail}", "");
            sbTemp = sbTemp.replace("${UpdateDuplicateCheck}", "");
            sbTemp = sbTemp.replace("${UpdateDuplicateCheckDetail}", "");
            sbTemp = sbTemp.replace("${AdditionServiceImplImport}", "");
        }

        if (file.getName().endsWith(DAO_END)) {
            boolean containDate = containDate(innerFieldList);
            sbTemp = sbTemp.replace("${AdditionDao}", geneAdditionDaoSb(innerFieldList));
            sbTemp =
                    sbTemp.replace("${AdditionDaoImport}", geneAdditionDaoImportSb(innerFieldList, clazz, containDate));
        } else {
            sbTemp = sbTemp.replace("${AdditionDao}", "");
            sbTemp = sbTemp.replace("${AdditionDaoImport}", "");
        }

        if (file.getName().endsWith(DAO_IMPL_END)) {
            boolean containDate = containDate(innerFieldList);
            sbTemp = sbTemp.replace("${AdditionDaoImpl}", geneAdditionDaoImplSb(innerFieldList));
            sbTemp = sbTemp
                    .replace("${AdditionDaoImplImport}", geneAdditionDaoImplImportSb(innerFieldList, clazz, containDate));
        } else {
            sbTemp = sbTemp.replace("${AdditionDaoImpl}", "");
            sbTemp = sbTemp.replace("${AdditionDaoImplImport}", "");
        }

        return sbTemp;
    }

    private StringBuilder geneAdditionDaoImportSb(List<InnerField> innerFieldList, Class clazz, boolean containDate) {
        StringBuilder additionDaoImportSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                additionDaoImportSb.append("import ").append(PathHelper.getVoClassName(clazz)).append(";\n");
                additionDaoImportSb.append("import ").append(PathHelper.getSoClassName(clazz)).append(";\n");
            }
        }
        if (containDate) {
            additionDaoImportSb.append("import java.util.Date;\n");
        }
        return additionDaoImportSb;
    }

    private StringBuilder geneAdditionServiceImplImportSb(Class clazz) {
        StringBuilder additionServiceImplImportSb = new StringBuilder();
        additionServiceImplImportSb.append("import java.util.Objects;\n")
                .append("import com.best.capital.pay.model.biz.common.exception.BizException;\n")
                .append("import com.best.capital.pay.model.biz.common.exception.ExceptionType;\n");
        return additionServiceImplImportSb;
    }

    private boolean containDate(List<InnerField> innerFieldList) {
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique() && innerField.getTypeClassName().equals(DATE_TYPE)) {
                return true;
            }
        }
        return false;
    }

    private StringBuilder geneAdditionDaoImplImportSb(List<InnerField> innerFieldList, Class clazz,
                                                      boolean containDate) {
        StringBuilder additionDaoImplImportSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                additionDaoImplImportSb.append("import ").append(PathHelper.getVoClassName(clazz)).append(";\n");
                additionDaoImplImportSb.append("import ").append(PathHelper.getSoClassName(clazz)).append(";\n");
            }
        }
        if (containDate) {
            additionDaoImplImportSb.append("import java.util.Date;\n");
        }
        return additionDaoImplImportSb;
    }

    private StringBuilder geneAdditionDaoSb(List<InnerField> innerFieldList) {
        StringBuilder requireDetailSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                String camelCaseColumnName = innerField.getCamelCaseColumnName();
                requireDetailSb.append("\t/**\n" + "     * 根据").append(camelCaseColumnName).append("查找\n")
                        .append("     * @param ").append(camelCaseColumnName).append(" 查询条件\n")
                        .append("     * @return 查找结果\n").append("     */\n")
                        .append("    ${SimpleName}Vo find${SimpleName}By")
                        .append(StringUtils.capitalize(innerField.getCamelCaseColumnName())).append("(")
                        .append(innerField.getTypeClassName()).append(" ").append(innerField.getCamelCaseColumnName())
                        .append(");\n\n");
            }

        }

        return requireDetailSb;
    }

    private StringBuilder geneAdditionDaoImplSb(List<InnerField> innerFieldList) {
        StringBuilder requireDetailSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                String camelCaseColumnName = innerField.getCamelCaseColumnName();
                String capitalizeCamelCaseColumnName = StringUtils.capitalize(camelCaseColumnName);
                requireDetailSb.append("\t/**\n" + "     * 根据").append(capitalizeCamelCaseColumnName).append("查找\n")
                        .append("     */\n").append("    @Override\n")
                        .append("    public ${SimpleName}Vo find${SimpleName}By").append(capitalizeCamelCaseColumnName)
                        .append("(").append(innerField.getTypeClassName()).append(" ").append(camelCaseColumnName)
                        .append(") {\n")
                        .append("        ${SimpleName}So ${LowerCaseSimpleName}So = new ${SimpleName}So();\n")
                        .append("        ${LowerCaseSimpleName}So.set").append(capitalizeCamelCaseColumnName).append("(")
                        .append(camelCaseColumnName).append(");\n")
                        .append("        return findVoBySoBase(${LowerCaseSimpleName}So);\n").append("    }\n\n");
            }

        }

        return requireDetailSb;
    }

    private StringBuilder geneRequireDetailSb(List<InnerField> innerFieldList) {
        StringBuilder requireDetailSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isCheckRequired()) {
                requireDetailSb.append("\t\tCheckEmptyHelper.check").append(innerField.getTypeClassName())
                        .append("(${LowerCaseSimpleName}.get")
                        .append(StringUtils.capitalize(innerField.getCamelCaseColumnName())).append("(), ").append("\"")
                        .append(innerField.getComment()).append("不能为空\");\n");
            }

        }
        return requireDetailSb;
    }

    private StringBuilder geneCreateDuplicateCheckSb(List<InnerField> innerFieldList) {
        StringBuilder createDuplicateCheckSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                createDuplicateCheckSb.append("\t\tcheck")
                        .append(StringUtils.capitalize(innerField.getCamelCaseColumnName()))
                        .append("Duplicate4Create(${LowerCaseSimpleName});\n");
            }

        }
        return createDuplicateCheckSb;
    }

    private StringBuilder geneCreateDuplicateCheckDetailSb(List<InnerField> innerFieldList) {
        StringBuilder createDuplicateCheckDetailSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                String camelCaseColumnName = innerField.getCamelCaseColumnName();
                String capitalizeCamelCaseColumnName = StringUtils.capitalize(camelCaseColumnName);
                createDuplicateCheckDetailSb.append("\tprivate void check").append(capitalizeCamelCaseColumnName)
                        .append("Duplicate4Create(${SimpleName}Vo ${LowerCaseSimpleName}) {\n").append(
                        "        ${SimpleName}Vo ${LowerCaseSimpleName}InDb = ${LowerCaseSimpleName}Dao.find${SimpleName}By")
                        .append(capitalizeCamelCaseColumnName).append("(${LowerCaseSimpleName}.get")
                        .append(capitalizeCamelCaseColumnName).append("());\n")
                        .append("        if (${LowerCaseSimpleName}InDb != null) {\n")
                        .append("            throw new BizException(\"存在").append(innerField.getComment())
                        .append("相同的产品\", ExceptionType.VIOLATE_BIZ_CHECK);\n").append("        }\n").append("    }\n\n");
            }

        }
        return createDuplicateCheckDetailSb;
    }

    private StringBuilder geneUpdateDuplicateCheckSb(List<InnerField> innerFieldList) {
        StringBuilder updateDuplicateCheckSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                updateDuplicateCheckSb.append("\t\tcheck")
                        .append(StringUtils.capitalize(innerField.getCamelCaseColumnName()))
                        .append("Duplicate4Update(${LowerCaseSimpleName});\n");
            }

        }
        return updateDuplicateCheckSb;
    }

    private StringBuilder geneUpdateDuplicateCheckDetailSb(List<InnerField> innerFieldList) {
        StringBuilder updateDuplicateCheckDetailSb = new StringBuilder();
        for (InnerField innerField : innerFieldList) {
            if (innerField.isUnique()) {
                String camelCaseColumnName = innerField.getCamelCaseColumnName();
                String capitalizeCamelCaseColumnName = StringUtils.capitalize(camelCaseColumnName);
                updateDuplicateCheckDetailSb.append("\tprivate void check").append(capitalizeCamelCaseColumnName)
                        .append("Duplicate4Update(${SimpleName}Vo ${LowerCaseSimpleName}) {\n").append(
                        "        ${SimpleName}Vo ${LowerCaseSimpleName}InDb = ${LowerCaseSimpleName}Dao.find${SimpleName}By")
                        .append(capitalizeCamelCaseColumnName).append("(${LowerCaseSimpleName}.get")
                        .append(capitalizeCamelCaseColumnName).append("());\n").append(
                        "        if (${LowerCaseSimpleName}InDb != null && !Objects.equals(${LowerCaseSimpleName}InDb.getId(), "
                                + "${LowerCaseSimpleName}.getId())) {\n").append("            throw new BizException(\"存在")
                        .append(innerField.getComment()).append("相同的产品\", ExceptionType.VIOLATE_BIZ_CHECK);\n")
                        .append("        }\n").append("    }\n\n");
            }

        }
        return updateDuplicateCheckDetailSb;
    }

    private StringBuilder geneBusinessStr(List<String> fileStrList) {
        StringBuilder businessStr = new StringBuilder();

        for (int i = 0; i < fileStrList.size(); i++) {
            String fileStr = fileStrList.get(i);
            if (fileStr.contains("@BizCode") && (i < fileStrList.size() - CommonConstant.TWO_INT)) {
                for (int j = i + 1; j < fileStrList.size(); j++) {
                    String nextFileStr = fileStrList.get(j);
                    if (isIgnoreClassAnnotation(nextFileStr)) {
                        continue;
                    }
                    if (isMethod(nextFileStr)) {
                        businessStr.append(nextFileStr).append("\n");
                        for (int k = j + 1; k < fileStrList.size(); k++) {
                            String innerFileStr = fileStrList.get(k);
                            businessStr.append(innerFileStr).append("\n");
                            if (innerFileStr.contains("}")) {
                                i = k;
                                break;
                            }

                        }
                        break;
                    }
                }
            }
        }
        return businessStr;
    }

}