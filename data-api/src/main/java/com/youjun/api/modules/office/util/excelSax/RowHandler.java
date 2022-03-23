package com.youjun.api.modules.office.util.excelSax;

import org.apache.poi.ss.usermodel.CellStyle;
import java.util.List;

/**
 * <p>
 *  excel 解析数据处理接口
 * </p>
 *
 * @author kirk
 * @since 2022/3/15
 */
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
     * 遍历完单个sheet所有行后 数据处理接口
     */
    default void doAfterAllAnalysed() {
    }
}

