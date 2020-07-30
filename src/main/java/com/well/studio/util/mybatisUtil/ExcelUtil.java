package com.well.studio.util.mybatisUtil;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.util.Iterator;

/**
 * excel生成帮助类
 */
public final class ExcelUtil {
    private ExcelUtil() {

    }

    /**
     * 创建单元格style
     *
     * @param workbook workbook
     * @param isPercent 是否需要设置为0.00%格式
     * @return style
     */
    public static CellStyle createCellStyle(Workbook workbook, Boolean isPercent) {
        // 创建单元格样式
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style.setWrapText(true);

        // 设置边框
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);

        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        style.setFont(font);

        // 设置0.00%格式
        if (isPercent) {
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        }

        return style;
    }

    /**
     * 创建表头
     *
     * @param cellStyle style
     */
    public static void createHeader(Row header, CellStyle cellStyle, String[] headerNames) {
        for (int i = 0; i < headerNames.length; i++) {
            createCell(header, cellStyle, i, headerNames[i]);
        }
    }

    /**
     * 创建表头
     *
     * @param cellStyle style
     */
    public static void createHeader(Row header, CellStyle cellStyle, Iterable<String> headerNames) {
        Iterator<String> iterator = headerNames.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String name = iterator.next();
            createCell(header, cellStyle, i, name);
            i++;
        }
    }

    /**
     * 设置列宽
     *
     * @param sheet sheet
     * @param colIndexs 需要设置列宽的indexs
     * @param width 具体列宽大小
     */
    public static void setHeaderWidth(Sheet sheet, int[] colIndexs, int width) {
        for (int colIndex : colIndexs) {
            sheet.setColumnWidth(colIndex, width);
        }
    }

    /**
     * 创建单元格
     *
     * @param row 行
     * @param style 样式
     * @param index 下标
     * @param value 单元格值
     */
    public static void createCell(Row row, CellStyle style, int index, String value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    public static void createCell(Row row, CellStyle style, int index, HSSFRichTextString value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    public static void createCell(Row row, CellStyle style, int index, Double value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public static void createCell(Row row, CellStyle style, int index, int value) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
}
