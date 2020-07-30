package com.well.studio.util.mybatisUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractGenerator {

    protected static final String BASE_MODEL_NAME = "AbstractBo";

    protected static final String BASE_VO_NAME = "AbstractVo";

    private static final Logger LOG = LoggerFactory.getLogger(AbstractGenerator.class);

    protected StringBuilder readFile(File file) {
        StringBuilder javaSb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                javaSb.append(line).append("\n");
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
        return javaSb;
    }

    protected List<String> readFileReturnList(File file) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                list.add(line);
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
        return list;
    }

    protected String replaceSpecialSymbols4BoAndVo(String orgFileStr, Class clazz, String packageName) {
        String simpleName = clazz.getSimpleName().replace("Vo", "");
        orgFileStr = orgFileStr.replaceAll("Vo", "");
        packageName = packageName.replaceAll("Vo", "");
        return replaceSpecialSymbols(simpleName, orgFileStr, clazz, packageName);
    }

    protected String replaceSpecialSymbols4Bo(String orgFileStr, Class clazz, String packageName) {
        String simpleName = clazz.getSimpleName();
        return replaceSpecialSymbols(simpleName, orgFileStr, clazz, packageName);
    }

    protected String replaceSpecialSymbols(String simpleName, String orgFileStr, Class clazz, String packageName) {
        String name = clazz.getName();
        orgFileStr = orgFileStr.replace("${SimpleName}", simpleName);
        orgFileStr = orgFileStr.replace("${Date}", DateFormatUtils.format(new Date(), "yyyy/MM/dd"));
        orgFileStr = orgFileStr.replace("${HistorySimpleName}", simpleName + "History");
        orgFileStr = orgFileStr.replace("${UpperCaseUnderlineSimpleName}", CamelCaseUtil.toUnderlineName(simpleName).toUpperCase());
        orgFileStr = orgFileStr.replace("${UnderlineSimpleName}", CamelCaseUtil.toUnderlineName(simpleName));
        orgFileStr = orgFileStr.replace("${LowerCaseSimpleName}", StringUtils.uncapitalize(simpleName));
        orgFileStr = orgFileStr.replace("${LowerCaseHistorySimpleName}", StringUtils.uncapitalize(simpleName) + "History");
        orgFileStr = orgFileStr.replace("${TableName}", GenerateCodeHelper.geneUpperCaseTableName(clazz));
        orgFileStr = orgFileStr.replace("${PackageName}", packageName.replace("\\", "."));
        orgFileStr = orgFileStr.replace("${ModelClassName}", name);
        orgFileStr = orgFileStr.replace("${ModelHistoryClassName}", name + "History");
        orgFileStr = orgFileStr.replace("${SoClassName}", PathHelper.getSoClassName(clazz));
        orgFileStr = orgFileStr.replace("${VoClassName}", PathHelper.getVoClassName(clazz));
        orgFileStr = orgFileStr.replace("${DaoClassName}", PathHelper.getDaoClassName(clazz));
        orgFileStr = orgFileStr.replace("${DaoMapperClassName}", PathHelper.getDaoMapperClassName(clazz));
        orgFileStr = orgFileStr.replace("${DaoHistoryMapperClassName}", PathHelper.getDaoHistoryMapperClassName(clazz));
        orgFileStr = orgFileStr.replace("${CommonServicePackageName}", PathHelper.getCommonServiceClassName(clazz));
        orgFileStr = orgFileStr.replace("${AdditionalServiceClassName}", PathHelper.getAdditionalServiceClassName(clazz));
        orgFileStr = orgFileStr.replace("${ServiceHelperClassName}", PathHelper.getServiceHelperClassName(clazz));
        return orgFileStr;
    }

    protected String replaceHistorySpecialSymbols(String orgFileStr, Class clazz, String packageName) {
        String simpleName = clazz.getSimpleName() + "History";
        String name = clazz.getName() + "History";
        orgFileStr = orgFileStr.replace("${SimpleName}", simpleName);
        orgFileStr = orgFileStr.replace("${Date}", DateFormatUtils.format(new Date(), "yyyy/MM/dd"));
        orgFileStr = orgFileStr.replace("${UpperCaseUnderlineSimpleName}", CamelCaseUtil.toUnderlineName(simpleName).toUpperCase());
        orgFileStr = orgFileStr.replace("${UnderlineSimpleName}", CamelCaseUtil.toUnderlineName(simpleName));
        orgFileStr = orgFileStr.replace("${LowerCaseSimpleName}", StringUtils.uncapitalize(simpleName));
        orgFileStr = orgFileStr.replace("${TableName}", GenerateCodeHelper.geneUpperCaseTableName(clazz) + "_HISTORY");
        orgFileStr = orgFileStr.replace("${PackageName}", packageName.replace("\\", "."));
        orgFileStr = orgFileStr.replace("${ModelClassName}", name);
        orgFileStr = orgFileStr.replace("${SoClassName}", PathHelper.getHistorySoClassName(clazz));
        orgFileStr = orgFileStr.replace("${VoClassName}", PathHelper.getHistoryVoClassName(clazz));
        orgFileStr = orgFileStr.replace("${DaoMapperClassName}", PathHelper.getDaoHistoryMapperClassName(clazz));
        return orgFileStr;
    }

    protected boolean isBizClass(List<String> fileStrList) {
        for (int i = 0; i < fileStrList.size(); i++) {
            String fileStr = fileStrList.get(i);
            if (fileStr.contains("@BizCode") && (i < fileStrList.size() - 2)) {
                for (int j = i + 1; j < fileStrList.size(); j++) {
                    String nextFileStr = fileStrList.get(j);
                    if (isIgnoreClassAnnotation(nextFileStr)) {
                        continue;
                    }
                    if (isClassOrInterface(nextFileStr)) {
                        return true;
                    }
                    if (isMethod(nextFileStr)) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    protected boolean isIgnoreClassAnnotation(String nextFileStr) {
        return nextFileStr.startsWith("@Service")
                || nextFileStr.startsWith("@Component")
                || nextFileStr.startsWith("@Repository")
                || nextFileStr.startsWith("@RestController")
                || nextFileStr.startsWith("@RequestMapping");
    }

    protected boolean isMethod(String nextFileStr) {
        return nextFileStr.contains("{");
    }

    protected boolean isClassOrInterface(String nextFileStr) {
        return nextFileStr.startsWith("public class") || nextFileStr.startsWith("public interface");
    }

}
