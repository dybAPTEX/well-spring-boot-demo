package com.well.studio.util.mybatisUtil;

public class PathHelper {

    public static String getDaoPackagePath(Class clazz) {
        return getDaoPackageName(clazz).replace(".", "\\");
    }

    public static String getDaoPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = replaceBoInPackageName(modelName, "dao");
        modelName = modelName.replace(".model.", ".dao.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    /**
     * 获取业务包名 example 包路径：com.best.capital.pay.model.biz.bo.sample 业务包名：sample
     *
     * @param clazz 类对象
     * @return 业务包名
     */
    public static String getBizPackageName(Class clazz) {
        String packageName = clazz.getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = Class.forName("com.well.studio.util.mybatisUtil.Sample");
        String packageName = clazz.getPackage().getName();
        String bizPackageName = packageName.substring(packageName.lastIndexOf(".") + 1);
        System.out.printf("");

    }

    public static String getDaoClassName(Class clazz) {
        return getDaoPackageName(clazz) + "." + clazz.getSimpleName() + "Dao";
    }

    public static String getDaoMapperPackagePath(Class clazz) {
        return getDaoMapperPackageName(clazz).replace(".", "\\");
    }

    public static String getDaoMapperPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = replaceBoInPackageName(modelName, "mapper");
        modelName = modelName.replace(".model.", ".mapper.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getDaoMapperClassName(Class clazz) {
        return getDaoMapperPackageName(clazz) + "." + clazz.getSimpleName() + "Mapper";
    }

    public static String getDaoHistoryMapperClassName(Class clazz) {
        return getDaoMapperPackageName(clazz) + "." + clazz.getSimpleName() + "HistoryMapper";
    }

    public static String getModelPackagePath(Class clazz) {
        return getModelPackageName(clazz).replace(".", "\\");
    }

    public static String getModelPackageName(Class clazz) {
        String modelName = clazz.getName();
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getVoPackagePath(Class clazz) {
        System.out.println("!!!"+getVoPackageName(clazz));
        return getVoPackageName(clazz).replace(".", "\\");
    }

    public static String getVoPackageName(Class clazz) {
        String modelName = clazz.getName();
        System.out.println("sada"+modelName);
        modelName = modelName.replace(".model.", ".vo.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getVoClassName(Class clazz) {
        return getVoPackageName(clazz) + "." + clazz.getSimpleName() + "Vo";
    }

    public static String getHistoryVoClassName(Class clazz) {
        return getVoPackageName(clazz) + "." + clazz.getSimpleName() + "HistoryVo";
    }

    public static String getSoPackagePath(Class clazz) {
        return getSoPackageName(clazz).replace(".", "\\");
    }

    public static String getSoPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = modelName.replace(".model.", ".so.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getSoClassName(Class clazz) {
        return getSoPackageName(clazz) + "." + clazz.getSimpleName() + "So";
    }

    public static String getHistorySoClassName(Class clazz) {
        return getSoPackageName(clazz) + "." + clazz.getSimpleName() + "HistorySo";
    }

    private static String getServicePackagePath(Class clazz) {
        return getServicePackageName(clazz).replace(".", "\\");
    }

    private static String getServicePackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = removeBoInPackageName(modelName);
        modelName = modelName.replace(".model.", ".service.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getAdditionalServicePackagePath(Class clazz) {
        return getAdditionalServicePackageName(clazz).replace(".", "\\");
    }

    public static String getAdditionalServicePackageName(Class clazz) {
        return getServicePackageName(clazz) + "." + "additional";
    }

    public static String getAdditionalServiceClassName(Class clazz) {
        return getAdditionalServicePackageName(clazz) + "." + clazz.getSimpleName() + "AdditionalService";
    }

    public static String getCommonServicePackagePath(Class clazz) {
        return getCommonServicePackageName(clazz).replace(".", "\\");
    }

    public static String getCommonServicePackageName(Class clazz) {
        return getServicePackageName(clazz) + "." + "common";
    }

    public static String getCommonServiceClassName(Class clazz) {
        return getCommonServicePackageName(clazz) + "." + clazz.getSimpleName() + "Service";
    }

    public static String getServiceHelperPackagePath(Class clazz) {
        return getServiceHelperPackageName(clazz).replace(".", "\\");
    }

    public static String getServiceHelperPackageName(Class clazz) {
        return getServicePackageName(clazz) + "." + "helper";
    }

    public static String getServiceHelperClassName(Class clazz) {
        return getServiceHelperPackageName(clazz) + "." + clazz.getSimpleName() + "ServiceHelper";
    }

    public static String getExportServicePackagePath(Class clazz) {
        return getExportServicePackageName(clazz).replace(".", "\\");
    }

    public static String getExportServicePackageName(Class clazz) {
        return getServicePackageName(clazz) + "." + "export";
    }

    public static String getExportServiceClassName(Class clazz) {
        return getExportServicePackageName(clazz) + "." + "Export" + clazz.getSimpleName();
    }

    public static String getImportServicePackagePath(Class clazz) {
        return getImportServicePackageName(clazz).replace(".", "\\");
    }

    public static String getImportServicePackageName(Class clazz) {
        return getServicePackageName(clazz) + "." + "imports";
    }

    public static String getImportServiceClassName(Class clazz) {
        return getImportServicePackageName(clazz) + "." + "Import" + clazz.getSimpleName();
    }

    public static String getExcelPackagePath(Class clazz) {
        return "excel\\template";
    }

    public static String getControllerPackagePath(Class clazz) {
        return getControllerPackageName(clazz).replace(".", "\\");
    }

    public static String getControllerPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = removeBoInPackageName(modelName);
        modelName = modelName.replace(".model.", ".controller.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getDaoMapperTestPackagePath(Class clazz) {
        return getDaoMapperTestPackageName(clazz).replace(".", "\\");
    }

    public static String getDaoMapperTestPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = removeBoInPackageName(modelName);
        modelName = modelName.replace(".model.", ".test.dao.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    public static String getServiceTestPackagePath(Class clazz) {
        return getServiceTestPackageName(clazz).replace(".", "\\");
    }

    public static String getServiceTestPackageName(Class clazz) {
        String modelName = clazz.getName();
        modelName = removeBoInPackageName(modelName);
        modelName = modelName.replace(".model.", ".test.service.");
        int index = modelName.lastIndexOf(".");
        return modelName.substring(0, index);
    }

    private static String removeBoInPackageName(String name) {
        return name.replace("bo.", "");
    }

    private static String replaceBoInPackageName(String packageName, String replaceToken) {
        return packageName.replace("bo", replaceToken);
    }

}
