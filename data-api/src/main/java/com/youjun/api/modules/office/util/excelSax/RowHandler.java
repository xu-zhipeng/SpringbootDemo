package com.youjun.api.modules.office.util.excelSax;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2022/3/15
 */

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;

@FunctionalInterface
public interface RowHandler {
    /**
     * 行数据处理接口
     * @param sheetIndex
     * @param sheetName
     * @param rowIndex
     * @param row
     */
    void handle(int sheetIndex, String sheetName, long rowIndex, List<Object> row);

    /**
     * 单元格数据处理接口
     * @param sheetIndex
     * @param sheetName
     * @param rowIndex
     * @param cellIndex
     * @param value
     * @param xssfCellStyle
     */
    default void handleCell(int sheetIndex, String sheetName, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {
    }

    /**
     * 所有数据处理完毕后收尾接口
     */
    default void doAfterAllAnalysed() {
    }
}

