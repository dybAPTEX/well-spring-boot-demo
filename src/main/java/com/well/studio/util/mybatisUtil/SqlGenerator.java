package com.well.studio.util.mybatisUtil;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SqlGenerator extends AbstractGenerator {

    public String genTable(Class clazz, boolean history) {

        String simpleName = geneSimpleName(clazz, history);

        List<InnerField> totalFieldList = geneInnerFieldList(clazz, history);

        String table = GenerateCodeHelper.geneTableName(simpleName);

        StringBuilder sb = new StringBuilder();
        sb.append("-- create table  ").append(table).append("\n");
        sb.append("create table ").append(table).append(" (\n");

        List<String> columns = getColumnToString(totalFieldList);
        for (String s : columns) {
            sb.append(s);
        }

        if (columns.size() > 0) {
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
        }
        sb.append(");\n");
        sb.append("------- ").append(table).append(" create end ------\n");

        System.out.println(sb.toString());

        return sb.toString();
    }

    private List<String> getColumnToString(List<InnerField> totalFieldList) {

        List<String> list = new ArrayList<>();
        for (InnerField field : totalFieldList) {

            String type = field.getTypeFullName();

            String colSql = getColumnSql(field.getUnderlineColumnName(), type);
            if (colSql != null && !colSql.isEmpty()) {
                list.add(colSql);
            }
        }
        return list;
    }

    private String getColumnSql(String name, String type) {
        boolean typeString = "java.lang.String".equals(type);
        boolean typeIntOrInteger = "int".equalsIgnoreCase(type) || "java.lang.Integer".equalsIgnoreCase(type);
        boolean typeDouble = "double".equalsIgnoreCase(type) || "java.lang.Double".equalsIgnoreCase(type);
        boolean typeFloat = "float".equalsIgnoreCase(type) || "java.lang.Float".equalsIgnoreCase(type);
        boolean typeDate = "java.sql.Date".equalsIgnoreCase(type) || "java.util.Date".equalsIgnoreCase(type);
        boolean typeLong = "long".equalsIgnoreCase(type) || "java.lang.Long".equalsIgnoreCase(type);
        boolean typeSetOrMapOrList = "java.util.Set".equalsIgnoreCase(type) || "java.util.Map".equalsIgnoreCase(type)
                || "java.util.List".equalsIgnoreCase(type);
        if (typeString) {
            return name + "  varchar2(255),\n";
        } else if (typeIntOrInteger) {
            return name + "  NUMBER(19),\n";
        } else if (typeDouble) {
            return name + "  NUMBER(19,4),\n";
        } else if (typeFloat) {
            return name + "  NUMBER(19,4),\n";
        } else if (typeDate) {
            return name + "  TIMESTAMP(6),\n";
        } else if (typeLong) {
            return name + "  NUMBER(19),\n";
        } else if (typeSetOrMapOrList) {
            return null;
        } else {
            return name + "  varchar2(255),\n";
        }
    }

    public String genPrimaryKey(Class clazz, boolean history) {
        StringBuilder key = new StringBuilder();

        String simpleName = geneSimpleName(clazz, history);
        String table = GenerateCodeHelper.geneTableName(simpleName);

        key.append("-- create key ").append(key).append("\n");
        key.append("alter table ").append(table).append(" add primary key (ID);");
        key.append("\n");

        System.out.println(key.toString());
        return key.toString();
    }

    public String genSeq(Class clazz, boolean history) {
        String simpleName = geneSimpleName(clazz, history);

        StringBuilder sequence = new StringBuilder();
        String table = GenerateCodeHelper.geneTableName(simpleName);
        String seqName = GenerateCodeHelper.geneSeqName(table);
        sequence.append("-- create sequence ").append(seqName).append("\n");
        sequence.append("create sequence ").append(seqName);
        sequence.append(" minvalue 1 ");
        sequence.append(" maxvalue 999999999999999999999999999 ");
        sequence.append(" start with 60000 ");
        sequence.append(" increment by 1 ");
        sequence.append(" cache 20; ");
        sequence.append("\n");
        System.out.println(sequence.toString());
        return sequence.toString();
    }

    public String genComment(Class clazz, boolean history) {
        List<InnerField> totalFieldListWithComment = geneInnerFieldWithCommentList(clazz, history);
        String simpleName = geneSimpleName(clazz, history);
        String tableComment = readTableCommentFromFile(clazz, history);

        String table = GenerateCodeHelper.geneTableName(simpleName);

        StringBuilder sb = new StringBuilder();
        sb.append("-- comment column ").append(table).append("\n");

        sb.append("COMMENT ON TABLE ").append(table).append(" IS '").append(tableComment).append("';\n");

        for (InnerField field : totalFieldListWithComment) {
            if (field.getComment() != null) {
                sb.append("comment on column ").append(table).append(".").append(field.getUnderlineColumnName()).append(" is '")
                        .append(field.getComment()).append("';\n");
            }

        }

        sb.append("------- comment ").append(table).append(" end ------\n");
        System.out.println(sb.toString());
        return sb.toString();
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

    private String geneSimpleName(Class clazz, Boolean history) {
        String simpleName;
        if (history) {
            simpleName = clazz.getSimpleName() + "History";
        } else {
            simpleName = clazz.getSimpleName();
        }
        return simpleName;
    }

    private String readTableCommentFromFile(Class clazz, boolean history) {
        String tableComment;
        if (history) {
            tableComment = GenerateCodeHelper.readHistoryTableCommentFromFile(clazz);
        } else {
            tableComment = GenerateCodeHelper.readTableCommentFromFile(clazz);
        }
        return tableComment;
    }

    private List<InnerField> geneInnerFieldWithCommentList(Class clazz, boolean history) {
        List<InnerField> totalFieldListWithComment;
        if (history) {
            totalFieldListWithComment = GenerateCodeHelper.geneHistoryInnerFieldWithComment(clazz);
        } else {
            totalFieldListWithComment = GenerateCodeHelper.geneInnerFieldWithComment(clazz);
        }
        return totalFieldListWithComment;
    }

}