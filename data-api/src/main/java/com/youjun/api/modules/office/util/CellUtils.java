package com.youjun.api.modules.office.util;

import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.cell.FormulaCellValue;
import com.youjun.common.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Excel cell  解析取值 设值工具
 * </p>
 *
 * @author kirk
 * @since 2021/8/5
 */
public class CellUtils {
    public static final DataFormatter formatter = new DataFormatter();

    public static Object getCellValue(Cell cell) {
        return getCellValue(cell, false, false);
    }

    public static Object getCellValue(Cell cell, boolean isFormatNumberic, boolean isSetMergeCellValue) {
        Object value = null;
        /**
         * isSetMergeCellValue
         * true ：让每个合并单元格 都有共同值
         * false ：合并单元格的首个单元格有值
         */
        if (isSetMergeCellValue) {
            Cell mergedCell = getMergedRegionCell(cell);
            if (mergedCell != cell) {
                cell = mergedCell;
            }
        }

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case _NONE:
                value = null;
                break;
            case NUMERIC:
                /**
                 * isFormatNumberic
                 * true ：转成 对应的 Date  Double Float BigDecimal
                 * false ：则直接将 NUMERIC 类型值转按照cell格式化成字符串
                 */
                if (isFormatNumberic) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue();
                    } else {
                        value = cell.getNumericCellValue();
                    }
                } else {
                    value = formatter.formatCellValue(cell);
                }

                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case BLANK:
                value = null;
                break;
            case ERROR:
                FormulaError error = FormulaError.forInt(cell.getErrorCellValue());
                value = null == error ? "" : error.getString();
                break;
            default:
                if (StringUtils.isNotBlank(cell.getStringCellValue())) {
                    value = cell.getStringCellValue();
                } else {
                    value = null;
                }
        }
        return value;
    }

    public static void setCellValue(Cell cell, Object value) {
        setCellValue(cell, value, cell.getCellStyle());
    }

    public static void setCellValue(Cell cell, Object value, StyleSet styleSet, boolean isHeader) {
        if (null != cell) {
            if (null != styleSet) {
                CellStyle headCellStyle = styleSet.getHeadCellStyle();
                CellStyle cellStyle = styleSet.getCellStyle();
                if (isHeader && null != headCellStyle) {
                    cell.setCellStyle(headCellStyle);
                } else if (null != cellStyle) {
                    cell.setCellStyle(cellStyle);
                }
            }

            if (value instanceof Date) {
                if (null != styleSet && null != styleSet.getCellStyleForDate()) {
                    cell.setCellStyle(styleSet.getCellStyleForDate());
                }
            } else if (value instanceof TemporalAccessor) {
                if (null != styleSet && null != styleSet.getCellStyleForDate()) {
                    cell.setCellStyle(styleSet.getCellStyleForDate());
                }
            } else if (value instanceof Calendar) {
                if (null != styleSet && null != styleSet.getCellStyleForDate()) {
                    cell.setCellStyle(styleSet.getCellStyleForDate());
                }
            } else if (value instanceof Number && (value instanceof Double || value instanceof Float || value instanceof BigDecimal) && null != styleSet && null != styleSet.getCellStyleForNumber()) {
                cell.setCellStyle(styleSet.getCellStyleForNumber());
            }

            setCellValue(cell, value, (CellStyle) null);
        }
    }

    public static void setCellValue(Cell cell, Object value, CellStyle style) {
        if (null != cell) {
            if (null != style) {
                cell.setCellStyle(style);
            }

            if (null == value) {
                cell.setCellValue("");
            } else if (value instanceof FormulaCellValue) {
                cell.setCellFormula(((FormulaCellValue) value).getValue());
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            } else if (value instanceof TemporalAccessor) {
                if (value instanceof Instant) {
                    cell.setCellValue(Date.from((Instant) value));
                } else if (value instanceof LocalDateTime) {
                    cell.setCellValue((LocalDateTime) value);
                } else if (value instanceof LocalDate) {
                    cell.setCellValue((LocalDate) value);
                }
            } else if (value instanceof Calendar) {
                cell.setCellValue((Calendar) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof RichTextString) {
                cell.setCellValue((RichTextString) value);
            } else if (value instanceof Number) {
                cell.setCellValue(((Number) value).doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }


    public static Cell getMergedRegionCell(Cell cell) {
        if (null == cell) {
            return null;
        }
        Cell cellIfMergedRegion = getCellIfMergedRegion(cell.getSheet(), cell.getColumnIndex(), cell.getRowIndex());
        return null == cellIfMergedRegion ? cell : cellIfMergedRegion;
    }

    private static Cell getCellIfMergedRegion(Sheet sheet, int x, int y) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            if (ca.isInRange(y, x)) {
                return getCell(sheet, ca.getFirstRow(), ca.getFirstColumn());
            }
        }

        return null;
    }

    public static Cell getCell(Sheet sheet, int rowIx, int colIx) {
        Row r = sheet.getRow(rowIx);
        if (r != null) {
            return r.getCell(colIx);
        }
        return null;
    }
}
