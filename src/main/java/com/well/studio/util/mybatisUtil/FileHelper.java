package com.well.studio.util.mybatisUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static File getMapperFile(Class clazz) {
        String mapperDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MAPPER_WORK + clazz.getSimpleName() + "Mapper.xml";
        return new File(mapperDirectory);
    }

    public static File getHistoryMapperFile(Class clazz) {
        String mapperDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MAPPER_WORK + clazz.getSimpleName() + "HistoryMapper.xml";
        return new File(mapperDirectory);
    }

    public static File getModelFile(Class clazz) {
        String voDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getModelPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + ".java";
        System.out.println(voDirectory);
        return new File(voDirectory);
    }

    public static File getHistoryModelFile(Class clazz) {
        String voDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getModelPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "History.java";
        return new File(voDirectory);
    }

    public static File getVoFile(Class clazz) {
        String voDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getVoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "Vo.java";
        return new File(voDirectory);
    }

    public static File getHistoryVoFile(Class clazz) {
        String voDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getVoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "HistoryVo.java";
        return new File(voDirectory);
    }

    public static File getSoFile(Class clazz) {
        System.out.println(PathHelper.getSoPackagePath(clazz));
        String soDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getSoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "So.java";
        return new File(soDirectory);
    }

    public static File getHistorySoFile(Class clazz) {
        String soDirectory = GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getSoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "HistorySo.java";
        return new File(soDirectory);
    }

    public static File getHistoryDaoMapperFile(Class clazz) {
        String daoMapperDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.DAO_WORK + PathHelper.getDaoMapperPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "HistoryMapper.java";
        return new File(daoMapperDirectory);
    }

    public static File getDaoMapperFile(Class clazz) {
        String daoMapperDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.DAO_WORK + PathHelper.getDaoMapperPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "Mapper.java";
        return new File(daoMapperDirectory);
    }

    public static File getDaoFile(Class clazz) {
        String daoDirectory = GenerateConfig.BASE_DIR + GenerateConfig.DAO_WORK + PathHelper.getDaoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "Dao.java";
        return new File(daoDirectory);
    }

    public static File getDaoImplFile(Class clazz) {
        String daoDirectory = GenerateConfig.BASE_DIR + GenerateConfig.DAO_WORK + PathHelper.getDaoPackagePath(clazz) + "\\" + clazz
                .getSimpleName() + "DaoImpl.java";
        return new File(daoDirectory);
    }

    public static File getDaoTestFile(Class clazz) {
        String daoTestDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.TEST_WORK + PathHelper.getDaoMapperTestPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "DaoTest.java";
        return new File(daoTestDirectory);
    }

    public static File getHistoryDaoMapperTestFile(Class clazz) {
        String daoMapperTestDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.TEST_WORK + PathHelper.getDaoMapperTestPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "HistoryMapperTest.java";
        return new File(daoMapperTestDirectory);
    }

    public static File getDaoMapperTestFile(Class clazz) {
        String daoMapperTestDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.TEST_WORK + PathHelper.getDaoMapperTestPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "MapperTest.java";
        return new File(daoMapperTestDirectory);
    }

    public static File getServiceFile(Class clazz) {
        String serviceDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getCommonServicePackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "Service.java";
        return new File(serviceDirectory);
    }

    public static File getServiceImplFile(Class clazz) {
        String serviceImplDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getCommonServicePackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "ServiceImpl.java";
        return new File(serviceImplDirectory);
    }

    public static File getServiceTestFile(Class clazz) {
        String daoTestDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.TEST_WORK + PathHelper.getServiceTestPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "ServiceTest.java";
        return new File(daoTestDirectory);
    }

    public static File getAdditionalServiceFile(Class clazz) {
        String serviceDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getAdditionalServicePackagePath(clazz) + "\\"
                        + clazz.getSimpleName() + "AdditionalService.java";
        return new File(serviceDirectory);
    }

    public static File getAdditionalServiceImplFile(Class clazz) {
        String serviceImplDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getAdditionalServicePackagePath(clazz) + "\\"
                        + clazz.getSimpleName() + "AdditionalServiceImpl.java";
        return new File(serviceImplDirectory);
    }

    public static File getServiceHelperFile(Class clazz) {
        String serviceDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getServiceHelperPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "ServiceHelper.java";
        return new File(serviceDirectory);
    }

    public static File getExportServiceFile(Class clazz) {
        String exportDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getExportServicePackagePath(clazz) + "\\"
                        + "Export" + clazz.getSimpleName() + ".java";
        return new File(exportDirectory);
    }

    public static File getExportTemplateFile(Class clazz) {
        String exportDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.XML_WORK + GenerateCodeHelper.geneUpperCaseTableName(clazz) + ".xml";
        return new File(exportDirectory);
    }

    public static File getImportServiceFile(Class clazz) {
        String exportDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.SERVICE_WORK + PathHelper.getImportServicePackagePath(clazz) + "\\"
                        + "Import" + clazz.getSimpleName() + ".java";
        return new File(exportDirectory);
    }

    public static File getImportTemplateFile(Class clazz) {
        String exportDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.XML_WORK + GenerateCodeHelper.geneUpperCaseTableName(clazz)
                        + "_IMPORT.xml";
        return new File(exportDirectory);
    }

    public static File getImportExcelFile(Class clazz) {
        String exportDirectory = GenerateConfig.BASE_DIR + GenerateConfig.EXCEL_WORK + clazz.getSimpleName() + "Uploading.xlsx";
        return new File(exportDirectory);
    }

    public static File getControllerFile(Class clazz) {
        String controllerDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.CONTROLLER_WORK + PathHelper.getControllerPackagePath(clazz) + "\\" + clazz
                        .getSimpleName() + "Controller.java";
        return new File(controllerDirectory);
    }

    public static File getPYModelFile(Class clazz) {
        String controllerDirectory =
                GenerateConfig.BASE_DIR + GenerateConfig.MODEL_WORK + PathHelper.getModelPackagePath(clazz) + "\\" + CamelCaseUtil.toUnderlineName(clazz.getSimpleName()) + ".py";
        return new File(controllerDirectory);
    }

    public static List<File> getAllFile2GeneList(Class clazz) {
        File mapperFile = getMapperFile(clazz);

        File voFile = getVoFile(clazz);
        File soFile = getSoFile(clazz);

        File daoFile = getDaoFile(clazz);
        File daoTestFile = getDaoMapperTestFile(clazz);

        File serviceFile = getServiceFile(clazz);
        File serviceImplFile = getServiceImplFile(clazz);
        File serviceHelperFile = getServiceHelperFile(clazz);
        File additionalServiceFile = getAdditionalServiceFile(clazz);
        File additionalServiceImplFile = getAdditionalServiceImplFile(clazz);

        File controllerFile = getControllerFile(clazz);

        List<File> file2GeneList = new ArrayList<>();
        file2GeneList.add(mapperFile);
        file2GeneList.add(voFile);
        file2GeneList.add(soFile);
        file2GeneList.add(daoFile);
        file2GeneList.add(daoTestFile);
        file2GeneList.add(serviceFile);
        file2GeneList.add(serviceImplFile);
        file2GeneList.add(serviceHelperFile);
        file2GeneList.add(additionalServiceFile);
        file2GeneList.add(additionalServiceImplFile);
        file2GeneList.add(controllerFile);
        return file2GeneList;
    }

}
