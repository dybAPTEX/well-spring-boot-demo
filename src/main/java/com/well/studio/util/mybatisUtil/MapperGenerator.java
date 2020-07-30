package com.well.studio.util.mybatisUtil;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class MapperGenerator extends AbstractGenerator {

    public String geneMapperStr(File tempFile, File mapperFile, Class clazz, boolean history) {

        String tempMapper = readFile(tempFile).toString();

        Properties prop = new Properties();

        String simpleName = geneSimpleName(clazz, history);
        String name = geneName(clazz, history);

        String voClassName = geneVoClassName(clazz, history);

        List<InnerField> totalFieldList = geneInnerFieldList(clazz, history);

        String business = genBusiness(mapperFile);
        String insert = genInsert(simpleName, totalFieldList);
        String batchInsert = this.genBatchInsert(simpleName, totalFieldList);
        String update = this.genUpdate(simpleName);
        String updateSetSql = this.genUpdateSetSql(totalFieldList);
        String where = this.genPageListClause(totalFieldList);
        String resultMap = this.genResultMap(totalFieldList);
        String table = GenerateCodeHelper.geneTableName(simpleName);

        prop.put("tablename", table);
        prop.put("modelname", simpleName);
        prop.put("modelClassName", name);
        prop.put("voClassName", voClassName);
        prop.put("insert", insert);
        prop.put("batchInsert", batchInsert);
        prop.put("where", where);
        prop.put("update", update);
        prop.put("updateSetSql", updateSetSql);
        prop.put("classvo", name + "Vo");
        prop.put("resultMap", resultMap);
        prop.put("class", name);
        prop.put("business", business);
        prop.put("modelpackge", PathHelper.getDaoMapperPackagePath(clazz).replace("\\", "."));
        prop.put("vopackge", PathHelper.getVoPackagePath(clazz).replace("\\", "."));

        Enumeration<Object> keys = prop.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = prop.getProperty(key);
            tempMapper = tempMapper.replaceAll("[$]\\{" + key + "\\}", value);
        }
        return tempMapper;
    }

    private String geneVoClassName(Class clazz, boolean history) {
        String voClassName;
        if (history) {
            voClassName = PathHelper.getHistoryVoClassName(clazz);
        } else {
            voClassName = PathHelper.getVoClassName(clazz);
        }
        return voClassName;
    }

    private String genBusiness(File mapperFile) {
        String business = "";
        if (mapperFile.exists()) {
            String mapper = readFile(mapperFile).toString();
            int businessBegin = mapper.indexOf("<!-- business begin -->");
            int businessEnd = mapper.indexOf("<!-- business end -->");
            if (businessBegin != -1 && businessEnd != -1) {
                business += mapper.substring(businessBegin, businessEnd);
                int index = business.indexOf(">");
                business = business.substring(index + 1);
            }
        } else {
            business = "\n\t<sql id=\"soSqlFilterRef\">\n\t</sql>\n\t";
        }

        return business;
    }

    private String geneName(Class clazz, boolean history) {
        String name;
        if (history) {
            name = clazz.getName() + "History";
        } else {
            name = clazz.getName();
        }
        return name;
    }

    private String geneSimpleName(Class clazz, Boolean history) {
        String simpleName;
        if (history) {
            simpleName = clazz.getSimpleName() + "History";
        } else {
            simpleName = clazz.getSimpleName();
        }
        return simpleName;
    }

    private List<InnerField> geneInnerFieldList(Class clazz, Boolean history) {
        List<InnerField> totalFieldList;
        if (history) {
            totalFieldList = GenerateCodeHelper.geneHistoryInnerFieldList(clazz);
        } else {
            totalFieldList = GenerateCodeHelper.geneInnerFieldList(clazz);
        }
        return totalFieldList;
    }

    private String genInsert(String simpleName, List<InnerField> totalFieldList) {

        String table = GenerateCodeHelper.geneTableName(simpleName);
        String seqName = GenerateCodeHelper.geneSeqName(table);

        StringBuffer sb = new StringBuffer();
        sb.append("\t\t<selectKey keyProperty=\"id\" resultType=\"long\" order=\"BEFORE\">\n");
        sb.append(" \t\t\tselect ").append(seqName).append(".nextval from dual\n");
        sb.append("\t\t</selectKey>\n");
        sb.append("\t\tinsert into ").append(table).append("(\n");

        buildSentence(sb, false, totalFieldList);

        sb.append("\t\t) \n");
        sb.append("\t\tvalues(\n");
        int index2 = 0;
        boolean isLastOuterItem2 = false;
        for (int i = 0; i < totalFieldList.size(); i++) {
            if (i == totalFieldList.size() - 1) {
                isLastOuterItem2 = true;
            }
            InnerField field = totalFieldList.get(i);
            sb.append("\t\t<!-- ").append(index2).append(" -->#{").append(field.getTotalName()).append("}");
            if (isLastOuterItem2) {
                sb.append("\n");
            } else {
                sb.append(",\n");
            }
            index2++;
        }
        sb.append("\t\t)\n");
        return sb.toString();
    }

    private String genBatchInsert(String simpleName, List<InnerField> totalFieldList) {

        String table = GenerateCodeHelper.geneTableName(simpleName);
        String seqName = GenerateCodeHelper.geneSeqName(table);

        StringBuffer sb = new StringBuffer();

        sb.append("insert into ").append(table).append("(\n");

        buildSentence(sb, false, totalFieldList);

        sb.append("\t\t) \n");

        sb.append("\t\tSELECT ").append(seqName).append(".NEXTVAL,A.* FROM (\n");
        sb.append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\"union all\">\n");
        sb.append("\t\t\tSELECT\n");
        int index2 = 0;
        boolean isLastOuterItem2 = false;
        for (int i = 0; i < totalFieldList.size(); i++) {
            if (i == totalFieldList.size() - 1) {
                isLastOuterItem2 = true;
            }
            InnerField field = totalFieldList.get(i);
            String fieldName = field.getCamelCaseColumnName();
            if ("id".equalsIgnoreCase(fieldName)) {
                continue;
            }
            sb.append("\t\t\t<!-- ").append(index2).append(" -->#{item.").append(field.getTotalName());

            Optional<JdbcTypeMappingEnum> jdbcTypeMappingEnum =
                    Optional.ofNullable(JdbcTypeMappingEnum.getJdbcTypeMappingEnumByTypeFullName(field.getTypeFullName()));
            sb.append(":").append(jdbcTypeMappingEnum.map(JdbcTypeMappingEnum::getJdbcType).orElse("VARCHAR"));
            sb.append("}");
            if (isLastOuterItem2) {
                sb.append("\n");
            } else {
                sb.append(",\n");
            }
            index2++;
        }
        sb.append("\t\t\tFROM DUAL\n");
        sb.append("\t\t</foreach>\n");
        sb.append("\t\t) A");
        return sb.toString();
    }

    private StringBuffer buildSentence(StringBuffer sb, boolean isLastOuterItem1, List<InnerField> totalFieldList) {
        int index1 = 0;
        for (int i = 0; i < totalFieldList.size(); i++) {
            if (i == totalFieldList.size() - 1) {
                isLastOuterItem1 = true;
            }
            InnerField field = totalFieldList.get(i);
            sb.append("\t\t<!-- ").append(index1).append(" -->").append(field.getUnderlineColumnName());
            if (isLastOuterItem1) {
                sb.append("\n");
            } else {
                sb.append(",\n");
            }
            index1++;
        }
        return sb;
    }

    private String genUpdate(String simpleName) {
        String table = GenerateCodeHelper.geneTableName(simpleName);

        return ("\t update " + table);
    }

    private String genUpdateSetSql(List<InnerField> totalFieldList) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        boolean isLastOuterItem = false;

        for (int i = 0; i < totalFieldList.size(); i++) {
            if (i == totalFieldList.size() - 1) {
                isLastOuterItem = true;
            }
            InnerField field = totalFieldList.get(i);
            String fieldName = field.getCamelCaseColumnName();
            if ("id".equalsIgnoreCase(fieldName)) {
                continue;
            }

            if ("lockVersion".equalsIgnoreCase(fieldName)) {
                sb.append("\t\t\t<!-- ").append(index).append(" -->").append(field.getUnderlineColumnName())
                        .append("=#{").append(field.getTotalName()).append("}+1");
                if (isLastOuterItem) {
                    sb.append("\n");
                } else {
                    sb.append(",\n");
                }
            } else {
                if (field.isRequired()) {
                    if ("java.lang.String".equals(field.getTypeFullName())) {
                        sb.append("\t\t\t<if test=\"").append(field.getTotalName()).append("!=null and ")
                                .append(field.getTotalName()).append("!='' \">\n");
                    } else {
                        sb.append("\t\t\t<if test=\"").append(field.getTotalName()).append(" !=null \">\n");
                    }
                    sb.append("\t\t\t<!-- ").append(index).append(" -->").append(field.getUnderlineColumnName())
                            .append("=#{").append(field.getTotalName()).append("}");
                    if (isLastOuterItem) {
                        sb.append("\n");
                    } else {
                        sb.append(",\n");
                    }
                    sb.append("\t\t\t</if>\n");
                } else {
                    sb.append("\t\t\t<!-- ").append(index).append(" -->").append(field.getUnderlineColumnName())
                            .append("=#{").append(field.getTotalName()).append("}");
                    if (isLastOuterItem) {
                        sb.append("\n");
                    } else {
                        sb.append(",\n");
                    }
                }
            }
            index++;
        }
        return sb.toString();
    }

    private String genPageListClause(List<InnerField> totalFieldList) {

        StringBuilder sb = new StringBuilder();

        for (InnerField field : totalFieldList) {
            String fieldName = field.getCamelCaseColumnName();
            if ("createdTime".equals(fieldName) || "updatedTime".equals(fieldName) || "lockVersion".equals(fieldName)) {
                continue;
            }
            String soFieldName = fieldName;
            if (field.getClassName() != null) {
                soFieldName = convert2SoName(field.getClassName()) + fieldName;
            }

            if ("java.lang.String".equals(field.getTypeFullName())) {
                sb.append("\t\t\t<if test=\"").append(soFieldName).append(" !=null and ").append(soFieldName)
                        .append(" !='' \">\n");
            } else {
                sb.append("\t\t\t<if test=\"").append(soFieldName).append(" !=null \">\n");
            }
            sb.append("\t\t\t\t and t.").append(field.getUnderlineColumnName()).append(" = #{").append(soFieldName)
                    .append("}\n");
            sb.append("\t\t\t</if>\n");
        }

        sb.append("\t\t\t<if test=\"idList !=null and idList.size() > 0 \">\n" + "\t\t\t\tand t.id in\n"
                + "\t\t\t\t<foreach collection=\"idList\" index=\"dex\" item=\"item\" open=\"(\"\n"
                + "\t\t\t\t\tclose=\")\" separator=\",\">\n" + "\t\t\t\t\t#{item}\n" + "\t\t\t\t</foreach>\n"
                + "\t\t\t</if>\n");
        return sb.toString();
    }

    private String convert2SoName(String className) {
        StringBuilder sb = new StringBuilder();
        String[] splitArray = className.split("\\.");

        if (splitArray.length > 0) {
            for (String aSplitArray : splitArray) {
                if (aSplitArray != null) {
                    sb.append(aSplitArray);
                    sb.append("So.");
                }

            }
        }
        return sb.toString();
    }

    /**
     * 生成Bo resultmap
     */
    private String genResultMap(List<InnerField> totalFieldList) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (InnerField field : totalFieldList) {
            sb.append("\t\t<!-- ").append(index).append(" --><result property=\"").append(field.getTotalName())
                    .append("\"  column=\"").append(field.getUnderlineColumnName()).append("\" />\n");
            index++;
        }

        return sb.toString();
    }

}
