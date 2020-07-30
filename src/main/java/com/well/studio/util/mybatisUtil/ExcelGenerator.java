package com.well.studio.util.mybatisUtil;

import com.well.studio.util.CommonConstant;

import java.util.List;
import com.site.lookup.util.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.util.stream.Collectors;

public class ExcelGenerator extends AbstractGenerator {

    public Workbook geneWorkbook(Class clazz) {
        Workbook workbook = new SXSSFWorkbook(CommonConstant.ONE_THOUSAND_INT);
        Sheet sheet = workbook.createSheet("Sheet1");
        Row header = sheet.createRow(0);

        String[] headerNames = getHeaderNames(clazz);

        ExcelUtil.createHeader(header, ExcelUtil.createCellStyle(workbook, false), headerNames);
        int[] colIndexes = getIndexes(headerNames);
        // 设定列宽
        ExcelUtil.setHeaderWidth(sheet, colIndexes, CommonConstant.ONE_THOUSAND_INT * CommonConstant.FIVE_INT);

        sheet.createFreezePane(0, 1, 0, 1);
        return workbook;
    }

    private int[] getIndexes(String[] headerNames) {
        int[] colIndexes = new int[headerNames.length];
        for (int i = 0; i < headerNames.length; i++) {
            colIndexes[i] = i;
        }
        return colIndexes;
    }

    private String[] getHeaderNames(Class clazz) {
        List<InnerField> innerFieldList = GenerateCodeHelper.geneInnerFieldWithComment(clazz);
        List<String> commentList = innerFieldList.stream().filter(
                item -> (StringUtils.isNotEmpty(item.getComment()) && !item.getDeclaringClass().equals(BASE_MODEL_NAME)))
                .map(InnerField::getComment).collect(Collectors.toList());
        return commentList.toArray(new String[commentList.size()]);
    }

}