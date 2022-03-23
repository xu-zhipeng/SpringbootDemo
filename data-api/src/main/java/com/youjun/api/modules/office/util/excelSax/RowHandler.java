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
    void handle(int sheetIndex, String sheetName, long rowIndex, List<Object> row);

    default void handleCell(int sheetIndex, String sheetName, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {
    }

    default void doAfterAllAnalysed() {
    }
}

