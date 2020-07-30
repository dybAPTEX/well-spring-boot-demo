package com.well.studio.util.mybatisUtil;

import java.io.File;
import java.net.URL;

public class TemplateFileHelper {

    public static File getMapperTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.MAPPER_TEMPLATE);
    }

    public static File getVoTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.VO_TEMPLATE);
    }

    public static File getSoTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.SO_TEMPLATE);
    }

    public static File getDaoMapperTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_MAPPER_TEMPLATE);
    }

    public static File getDaoTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_TEMPLATE);
    }

    public static File getDaoImplTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_IMPL_TEMPLATE);
    }

    public static File getNoHistoryDaoImplTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.NO_HISTORYDAO_IMPL_TEMPLATE);
    }

    public static File getDaoTestTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_TEST_TEMPLATE);
    }

    public static File getDaoMapperTestTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_MAPPER_TEST_TEMPLATE);
    }

    public static File getDaoHistoryMapperTestTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.DAO_HISTORY_MAPPER_TEST_TEMPLATE);
    }

    public static File getServiceTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.SERVICE_TEMPLATE);
    }

    public static File getServiceImplTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.SERVICE_IMPL_TEMPLATE);
    }

    public static File getServiceTestTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.SERVICE_TEST_TEMPLATE);
    }

    public static File getAdditionalServiceTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.ADDITIONAL_SERVICE_TEMPLATE);
    }

    public static File getAdditionalServiceImplTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.ADDITIONAL_SERVICE_IMPL_TEMPLATE);
    }

    public static File getServiceHelperTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.SERVICE_HELPER_TEMPLATE);
    }

    public static File getExportServiceTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.EXPORT_TEMPLATE);
    }

    public static File getExportTemplateTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.EXPORT_TEMPLATE_TEMPLATE);
    }

    public static File getImportServiceTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.IMPORT_TEMPLATE);
    }

    public static File getImportTemplateTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.IMPORT_TEMPLATE_TEMPLATE);
    }

    public static File getControllerTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.CONTROLLER_TEMPLATE);
    }

    public static File getTempFile(BaseGenerateCode gen, String classPath) {
        Class c = gen.getClass();
        URL url=c.getResource(classPath);
        String fi=url.getPath();
        return new File(fi);
    }

    public static File getPYModelTempFile(BaseGenerateCode gen) {
        return getTempFile(gen, GenerateConfig.PY_MODEL_TEMPLATE);
    }

}
