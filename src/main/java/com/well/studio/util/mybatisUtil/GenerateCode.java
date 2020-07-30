package com.well.studio.util.mybatisUtil;

import com.well.studio.enums.GeneType;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * 代码自动生成2017十月版 (逆向工程)
 */
public class GenerateCode extends BaseGenerateCode {

    private static final String HISTORY = "History";

    private static final Logger LOG = LoggerFactory.getLogger(GenerateCode.class);

    @SuppressWarnings({ "rawtypes" })
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IOException {

        Scanner scan = new Scanner(System.in, StandardCharsets.UTF_8.name());

        System.out.println("生成过程开始，是否要生成操作记录对象：y,n");
        String recordHistory = scan.next().trim();

        System.out.println("是否要生成 table： y,n");
        String str1 = scan.next().trim();

        System.out.println("是否要生成 Mapper：y,n");
        String str2 = scan.next().trim();

        System.out.println("是否要生成 Vo\\So：y,n");
        String str3 = scan.next().trim();

        System.out.println("是否要生成 Dao：y,n");
        String str4 = scan.next().trim();

        System.out.println("是否要生成 Service：y,n");
        String str5 = scan.next().trim();

        System.out.println("是否要生成 Controller：y,n");
        String str6 = scan.next().trim();

        System.out.println("请输入要生成model的全名 ,按回车结束 ,如：com.oasis.wyvern.dispatch.model.system.tool.PredefinedCode,多个类请用;隔开");
        String str0 = scan.next().trim();

        List<Class> clazzList = loadClassList(str0);

        GenerateCode gen = new GenerateCode();
        String y = "y";

        for (Class clazz : clazzList) {
            if (clazz.getSimpleName().endsWith(HISTORY)) {
                System.out.println("--无法单独生成History的代码");
                return;
            }

            if (y.equalsIgnoreCase(recordHistory)) {
                gen.genHistoryBo(clazz);
            }

            if (y.equalsIgnoreCase(str1)) {
                SqlGenerator sqlGenerator = new SqlGenerator();
                sqlGenerator.genTable(clazz, false);
                sqlGenerator.genPrimaryKey(clazz, false);
                sqlGenerator.genSeq(clazz, false);
                sqlGenerator.genComment(clazz, false);
                if (y.equalsIgnoreCase(recordHistory)) {
                    sqlGenerator.genTable(clazz, true);
                    sqlGenerator.genPrimaryKey(clazz, true);
                    sqlGenerator.genSeq(clazz, true);
                    sqlGenerator.genComment(clazz, true);
                }
            }

            if (y.equalsIgnoreCase(str2)) {
                gen.genMapper(clazz, gen, false);
                if (y.equalsIgnoreCase(recordHistory)) {
                    gen.genMapper(clazz, gen, true);
                }
            }

            if (y.equalsIgnoreCase(str3)) {
                gen.genVo(clazz);
                gen.genSo(clazz);
                if (y.equalsIgnoreCase(recordHistory)) {
                    gen.genHistoryVo(clazz);
                    gen.genHistorySo(clazz);
                }
            }

            if (y.equalsIgnoreCase(str4)) {
                gen.genDaoMapper(clazz, gen, false);
                gen.genDao(clazz, gen);
                if (y.equalsIgnoreCase(recordHistory)) {
                    gen.genDaoImpl(clazz, gen);
                } else {
                    gen.genNoHistoryDaoImpl(clazz, gen);
                }
                gen.genDaoMapperTest(clazz, gen, false);
                gen.genDaoTest(clazz, gen);

                if (y.equalsIgnoreCase(recordHistory)) {
                    gen.genDaoMapper(clazz, gen, true);
                    gen.genDaoMapperTest(clazz, gen, true);
                }
            }

            if (y.equalsIgnoreCase(str5)) {
                gen.genService(clazz, gen);
                gen.genServiceImpl(clazz, gen);
                gen.genAdditionalService(clazz, gen);
                gen.genAdditionalServiceImpl(clazz, gen);
                gen.genServiceHelper(clazz, gen);
                gen.genExportService(clazz, gen);
                gen.genExportTemplate(clazz, gen);
                gen.genServiceTest(clazz, gen);
            }

            if (y.equalsIgnoreCase(str6)) {
                gen.genController(clazz, gen);
            }
        }
        scan.close();
    }

    private void genController(Class clazz, GenerateCode gen) {
        File controllerFile = FileHelper.getControllerFile(clazz);
        File controllerTempFile = TemplateFileHelper.getControllerTempFile(gen);
        genByTemplate(controllerFile, controllerTempFile, clazz, PathHelper.getControllerPackagePath(clazz));
    }

    private void genServiceImpl(Class clazz, GenerateCode gen) {
        File serviceImplFile = FileHelper.getServiceImplFile(clazz);
        File serviceImplTempFile = TemplateFileHelper.getServiceImplTempFile(gen);
        genByTemplate(serviceImplFile, serviceImplTempFile, clazz, PathHelper.getCommonServicePackagePath(clazz));
    }

    private void genService(Class clazz, GenerateCode gen) {
        File serviceFile = FileHelper.getServiceFile(clazz);
        File serviceTempFile = TemplateFileHelper.getServiceTempFile(gen);
        genByTemplate(serviceFile, serviceTempFile, clazz, PathHelper.getCommonServicePackagePath(clazz));
    }

    private void genServiceTest(Class clazz, GenerateCode gen) {
        File serviceTestFile = FileHelper.getServiceTestFile(clazz);
        File serviceTestTempFile = TemplateFileHelper.getServiceTestTempFile(gen);
        genByTemplate(serviceTestFile, serviceTestTempFile, clazz, PathHelper.getServiceTestPackageName(clazz));
    }

    private void genAdditionalServiceImpl(Class clazz, GenerateCode gen) {
        File additionalServiceImplFile = FileHelper.getAdditionalServiceImplFile(clazz);
        File additionalServiceImplTempFile = TemplateFileHelper.getAdditionalServiceImplTempFile(gen);
        genByTemplate(additionalServiceImplFile, additionalServiceImplTempFile, clazz,
                PathHelper.getAdditionalServicePackagePath(clazz));
    }

    private void genAdditionalService(Class clazz, GenerateCode gen) {
        File additionalServiceFile = FileHelper.getAdditionalServiceFile(clazz);
        File additionalServiceTempFile = TemplateFileHelper.getAdditionalServiceTempFile(gen);
        genByTemplate(additionalServiceFile, additionalServiceTempFile, clazz, PathHelper.getAdditionalServicePackagePath(clazz));
    }

    private void genServiceHelper(Class clazz, GenerateCode gen) {
        File serviceHelperImplFile = FileHelper.getServiceHelperFile(clazz);
        File serviceHelperImplTempFile = TemplateFileHelper.getServiceHelperTempFile(gen);
        genByTemplate(serviceHelperImplFile, serviceHelperImplTempFile, clazz, PathHelper.getServiceHelperPackagePath(clazz));
    }

    private void genExportService(Class clazz, GenerateCode gen) {
        File exportServiceFile = FileHelper.getExportServiceFile(clazz);
        File exportServiceTempFile = TemplateFileHelper.getExportServiceTempFile(gen);
        genByTemplate(exportServiceFile, exportServiceTempFile, clazz, PathHelper.getExportServicePackagePath(clazz));
    }

    private void genExportTemplate(Class clazz, GenerateCode gen) {
        File exportTemplateFile = FileHelper.getExportTemplateFile(clazz);
        File exportTemplateTempFile = TemplateFileHelper.getExportTemplateTempFile(gen);
        genXmlByTemplate(exportTemplateFile, exportTemplateTempFile, clazz, PathHelper.getExcelPackagePath(clazz));
    }

    private void genImportService(Class clazz, GenerateCode gen) {
        File importServiceFile = FileHelper.getImportServiceFile(clazz);
        File importServiceTempFile = TemplateFileHelper.getImportServiceTempFile(gen);
        genByTemplate(importServiceFile, importServiceTempFile, clazz, PathHelper.getImportServicePackagePath(clazz));
    }

    private void genImportTemplate(Class clazz, GenerateCode gen) {
        File importTemplateFile = FileHelper.getImportTemplateFile(clazz);
        File importTemplateTempFile = TemplateFileHelper.getImportTemplateTempFile(gen);
        genXmlByTemplate(importTemplateFile, importTemplateTempFile, clazz, PathHelper.getExcelPackagePath(clazz));
    }

    private void genVo(Class clazz) {
        File voFile = FileHelper.getVoFile(clazz);
        genBean(voFile, clazz, GeneType.VO);
    }

    private void genSo(Class clazz) {
        File soFile = FileHelper.getSoFile(clazz);
        genBean(soFile, clazz, GeneType.SO);
    }

    private void genHistorySo(Class clazz) {
        File soFile = FileHelper.getHistorySoFile(clazz);
        genBean(soFile, clazz, GeneType.HISTORY_SO);
    }

    private File genHistoryBo(Class clazz) {
        File historyFile = FileHelper.getHistoryModelFile(clazz);
        genBean(historyFile, clazz, GeneType.HISTORY_BO);
        return historyFile;
    }

    private void genHistoryVo(Class clazz) {
        File voFile = FileHelper.getHistoryVoFile(clazz);
        genBean(voFile, clazz, GeneType.HISTORY_VO);
    }

    private void genDaoMapper(Class clazz, GenerateCode gen, boolean history) {
        File daoMapperTempFile = TemplateFileHelper.getDaoMapperTempFile(gen);
        if (history) {
            File daoMapperFile = FileHelper.getHistoryDaoMapperFile(clazz);
            genHistoryByTemplate(daoMapperFile, daoMapperTempFile, clazz, PathHelper.getDaoMapperPackagePath(clazz));
        } else {
            File daoMapperFile = FileHelper.getDaoMapperFile(clazz);
            genByTemplate(daoMapperFile, daoMapperTempFile, clazz, PathHelper.getDaoMapperPackagePath(clazz));
        }
    }

    private void genDao(Class clazz, GenerateCode gen) {
        File daoFile = FileHelper.getDaoFile(clazz);
        File daoTempFile = TemplateFileHelper.getDaoTempFile(gen);
        genByTemplate(daoFile, daoTempFile, clazz, PathHelper.getDaoPackagePath(clazz));
    }

    private void genDaoImpl(Class clazz, GenerateCode gen) {
        File daoImplFile = FileHelper.getDaoImplFile(clazz);
        File daoImplTempFile = TemplateFileHelper.getDaoImplTempFile(gen);
        genByTemplate(daoImplFile, daoImplTempFile, clazz, PathHelper.getDaoPackagePath(clazz));
    }

    private void genNoHistoryDaoImpl(Class clazz, GenerateCode gen) {
        File daoImplFile = FileHelper.getDaoImplFile(clazz);
        File daoImplTempFile = TemplateFileHelper.getNoHistoryDaoImplTempFile(gen);
        genByTemplate(daoImplFile, daoImplTempFile, clazz, PathHelper.getDaoPackagePath(clazz));
    }

    private void genDaoMapperTest(Class clazz, GenerateCode gen, boolean history) {
        if (history) {
            File daoHistoryMapperTestTempFile = TemplateFileHelper.getDaoHistoryMapperTestTempFile(gen);
            File daoHistoryMapperTestFile = FileHelper.getHistoryDaoMapperTestFile(clazz);
            genHistoryByTemplate(daoHistoryMapperTestFile, daoHistoryMapperTestTempFile, clazz,
                    PathHelper.getDaoMapperTestPackageName(clazz));
        } else {
            File daoMapperTestTempFile = TemplateFileHelper.getDaoMapperTestTempFile(gen);
            File daoMapperTestFile = FileHelper.getDaoMapperTestFile(clazz);
            genByTemplate(daoMapperTestFile, daoMapperTestTempFile, clazz, PathHelper.getDaoMapperTestPackageName(clazz));
        }

    }

    private void genDaoTest(Class clazz, GenerateCode gen) {
        File daoTestFile = FileHelper.getDaoTestFile(clazz);
        File daoTestTempFile = TemplateFileHelper.getDaoTestTempFile(gen);
        genByTemplate(daoTestFile, daoTestTempFile, clazz, PathHelper.getDaoMapperTestPackageName(clazz));
    }

    private String genByTemplate(File file, File tempFile, Class clazz, String packageName) {
        FileWriter fw = null;
        String javaStr = new JavaGenerator().geneJavaStr(file, tempFile, clazz, packageName, false);
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new IOException("没有创建文件");
            }
            System.out.println("--" + file.getAbsoluteFile());
            fw = new FileWriter(file);
            fw.write(javaStr);

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
        return null;
    }

    private String genHistoryByTemplate(File file, File tempFile, Class clazz, String packageName) {
        FileWriter fw = null;
        String javaStr = new JavaGenerator().geneJavaStr(file, tempFile, clazz, packageName, true);
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new IOException("没有创建文件");
            }
            System.out.println("--" + file.getAbsoluteFile());
            fw = new FileWriter(file);
            fw.write(javaStr);

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
        return null;
    }

    private void genBean(File file, Class clazz, GeneType geneType) {
        FileWriter fw = null;
        String beanStr = new BeanGenerator().geneBeanStr(file, clazz, geneType);
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return;
            }
            System.out.println("--" + file.getAbsoluteFile());
            fw = new FileWriter(file);
            fw.write(beanStr);

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
    }

    private void genBeanByTemplate(File file, File tempFile, Class clazz, String packageName) {
        FileWriter fw = null;
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return;
            }
            System.out.println("--" + file.getAbsoluteFile());
            fw = new FileWriter(file);
            fw.write(new BeanGenerator().geneBeanStr(tempFile, clazz, packageName));

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
    }

    private void genXmlByTemplate(File file, File tempFile, Class clazz, String packageName) {
        FileWriter fw = null;
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return;
            }
            System.out.println("--" + file.getAbsoluteFile());
            fw = new FileWriter(file);
            fw.write(new XmlGenerator().geneBeanStr(tempFile, clazz, packageName));

        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
    }

    private void genExcel(Class clazz) {
        FileOutputStream fos = null;
        try {
            File file = FileHelper.getImportExcelFile(clazz);
            System.out.println("--" + file.getAbsoluteFile());
            fos = new FileOutputStream(file);

            Workbook workbook =  new ExcelGenerator().geneWorkbook(clazz);

            workbook.write(fos);
        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                LOG.error("异常", e);
            }
        }
    }

    /**
     * 生成mapper 文件
     */
    private void genMapper(Class clazz, GenerateCode gen, boolean history) {
        File mapperFile;
        if (history) {
            mapperFile = FileHelper.getHistoryMapperFile(clazz);
        } else {
            mapperFile = FileHelper.getMapperFile(clazz);
        }

        File mapperTempFile = TemplateFileHelper.getMapperTempFile(gen);
        FileWriter fw = null;

        String mapperStr = new MapperGenerator().geneMapperStr(mapperTempFile, mapperFile, clazz, history);
        try {
            if (!mapperFile.getParentFile().exists() && !mapperFile.getParentFile().mkdirs()) {
                return;
            }
            fw = new FileWriter(mapperFile);
            System.out.println("--" + mapperFile.getAbsolutePath());
            fw.write(mapperStr);
        } catch (IOException e) {
            LOG.error("异常", e);
        } finally {
            finalMethod(fw);
        }
    }

    /**
     * 关闭流
     *
     * @param fw fw
     */
    private void finalMethod(FileWriter fw) {
        try {
            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            LOG.error("异常", e);
        }
    }

    /**
     * 检查待生成的文件是否存在 如果存在一个同名的文件则任务生成该文件有异常
     */
    private boolean allFileExists(Class clazz) {
        List<File> file2GeneList = FileHelper.getAllFile2GeneList(clazz);
        for (File file : file2GeneList) {
            if (file.exists()) {
                System.out.println(file.getAbsolutePath() + "已经存在，请检查");
                return false;
            }
        }
        return true;
    }

}

