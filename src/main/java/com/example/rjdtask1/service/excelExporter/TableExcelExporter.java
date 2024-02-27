package com.example.rjdtask1.service.excelExporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.poi.xssf.usermodel.*;

import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class TableExcelExporter<T> {

    protected void writeHeaderLine(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet sheet = workbook.createSheet(sheetName);

        XSSFRow row = sheet.createRow(0);

        List<String> names = getHeaderNames();

        for (int i = 0; i < names.size(); i++) {
            createCell(row, i, names.get(i), getHeaderStyle(workbook));
        }

        //setHeaderBorders(row);
        stylizeBackgroundHeaderRow(row);
    }

    protected List<String> getHeaderNames() {
        return null;
    };

    protected void createCell(XSSFRow row, int columnCount, Object value, XSSFCellStyle style) {
        XSSFCell cell = row.getCell(columnCount);
        if (cell == null) {
            cell = row.createCell(columnCount);
        }
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else if (value instanceof Float){
            cell.setCellValue((Float) value);
        } else {
            cell.setCellValue((String) value);
        }
        //cell.setCellStyle(style);
    }


    private void manualSizeWidthColumns(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFCell maxCellWidth = sheet.getRow(0).getCell(1);
        double maxWidth = 0;
        Map<Integer, Double> widths = new HashMap<>();
        for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            widths.put(i, (double)0);
        }

        for (Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
            XSSFRow row = (XSSFRow) it.next();
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {

                XSSFCell cellNext = row.getCell(i);
                double cellWidth = 0;
                if (cellNext.getCellType().equals(CellType.NUMERIC)) {
                    String cellText = Double.toString(cellNext.getNumericCellValue());
                    cellWidth = getCellWidth(cellText, cellNext.getCellStyle().getFont());
                }
                else {
                    String cellText = cellNext.getStringCellValue();
                    cellWidth = getCellWidth(cellText, cellNext.getCellStyle().getFont());
                }

                if (widths.get(i) < cellWidth) {
                    widths.put(i, cellWidth);
                }
            }
        }

        int defaultCharWidth = SheetUtil.DEFAULT_CHAR_WIDTH;
        for (Map.Entry<Integer, Double> entry: widths.entrySet()) {
            sheet.setColumnWidth(entry.getKey(), (int)Math.round(entry.getValue() / defaultCharWidth * 256));
        }
    }

    private void autoWidthColumns(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int)(sheet.getColumnWidth(i)*1.1));
        }
    }
    public abstract void exportToStream(ByteArrayOutputStream stream, String year) throws IOException;
    private double getCellWidth(String cellText, Font font) {
        AttributedString attributedString = new AttributedString(cellText);
        attributedString.addAttribute(TextAttribute.FAMILY, font, 0, cellText.length());
        attributedString.addAttribute(TextAttribute.SIZE, (float)font.getFontHeightInPoints());
        if (font.getBold()) attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD,  0, cellText.length());
        if (font.getItalic()) attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE,  0, cellText.length());

        FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);

        TextLayout layout = new TextLayout(attributedString.getIterator(), fontRenderContext);
        Rectangle2D bounds = layout.getBounds();
        double frameWidth = bounds.getWidth();

        return frameWidth;
    }

    protected void setTableBorders(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        CellRangeAddress cellAddresses = new CellRangeAddress(0, sheet.getLastRowNum(), 0, sheet.getRow(0).getLastCellNum() - 1);

        RegionUtil.setBorderTop(BorderStyle.MEDIUM, cellAddresses, sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellAddresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cellAddresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellAddresses, sheet);
    }

    protected void setDataRowBorders(XSSFRow row) {
        XSSFSheet sheet = row.getSheet();

        CellRangeAddress cellAddressesFirstCol = new CellRangeAddress(
                row.getRowNum(),
                row.getRowNum(),
                0,
                0
        );

        RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddressesFirstCol, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellAddressesFirstCol, sheet);

        for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
            CellRangeAddress cellAddressesOther = new CellRangeAddress(
                    row.getRowNum(),
                    row.getRowNum(),
                    i,
                    i
            );

            RegionUtil.setBorderBottom(BorderStyle.THIN, cellAddressesOther, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellAddressesOther, sheet);
        }
    }

    protected void setHeaderBorders(XSSFRow row) {
        XSSFSheet sheet = row.getSheet();

        CellRangeAddress cellAddressesFirstCol = new CellRangeAddress(
                row.getRowNum(),
                row.getRowNum(),
                0,
                0
        );

        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellAddressesFirstCol, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellAddressesFirstCol, sheet);

        for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
            CellRangeAddress cellAddressesOther = new CellRangeAddress(
                    row.getRowNum(),
                    row.getRowNum(),
                    i,
                    i
            );

            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellAddressesOther, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cellAddressesOther, sheet);
        }
    }

    protected XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        String fontName = "Cascadia Mono";
        font.setFontName(fontName);
        //font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        return style;
    }

    protected XSSFCellStyle getDataStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Cascadia Mono");
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        return style;
    }

    protected void stylizeBackgroundHeaderRow(XSSFRow row) {
        for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
            Cell cell = it.next();

            cell.getCellStyle().setFillForegroundColor(new XSSFColor(new byte[]{(byte) 217, (byte)217, (byte)217}));
            cell.getCellStyle().setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
    }

    protected void stylizeBackgroundDataRow(XSSFRow row) {
        XSSFColor color;
        if (row.getRowNum() % 2 == 0) {
            color = new XSSFColor(new byte[]{(byte) 242, (byte)242, (byte)242});
        }
        else {
            color = new XSSFColor(new byte[]{(byte) 255, (byte)255, (byte)255});
        }

        for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
            Cell cell = it.next();

            cell.getCellStyle().setFillForegroundColor(color);
            cell.getCellStyle().setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
    }
}
