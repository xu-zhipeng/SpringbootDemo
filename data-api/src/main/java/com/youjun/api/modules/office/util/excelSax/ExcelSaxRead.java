package com.youjun.api.modules.office.util.excelSax;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import com.youjun.common.util.CollectionUtils;
import com.youjun.common.util.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * excel SAX 解析工具
 * 注：new 实例调用
 * </p>
 *
 * @author kirk
 * @since 2022/3/14
 */
public class ExcelSaxRead {
    private boolean firstFlag = true;
    private int currentSheetIndex = 0;
    private String currentSheetName = null;
    private Map<String, Object[][]> dataArray = new HashMap<>();
    private List<Object[]> rowList = new ArrayList<>();

    public Map<String, Object[][]> read(InputStream in) {
        in = IoUtil.toMarkSupportStream(in);
        RowHandler rowHandler = createRowHandler();
        ExcelSaxReader<?> reader = (ExcelFileUtil.isXlsx(in) ? new Excel07SaxReader(rowHandler) : new Excel03SaxReader(rowHandler));
        reader.read(in, -1);
        //最后一个sheet遍历结束 push row
        Object[] rowArray = rowList.toArray();
        Object[][] sheetArray = new Object[rowList.size()][];
        System.arraycopy(rowArray, 0, sheetArray, 0, rowArray.length);
        dataArray.put(currentSheetName, sheetArray);
        return dataArray;
    }

    /**
     * Sax解析excel 行处理
     *
     * @return
     */
    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, String sheetName, long rowIndex, List<Object> row) {
                if (firstFlag) {
                    currentSheetName = sheetName;
                    firstFlag = false;
                }
                if (currentSheetIndex != sheetIndex) {
                    //sheet遍历结束 push row
                    Object[] rowArray = rowList.toArray();
                    Object[][] sheetArray = new Object[rowList.size()][];
                    System.arraycopy(rowArray, 0, sheetArray, 0, rowArray.length);
                    dataArray.put(currentSheetName, sheetArray);
                    currentSheetIndex = sheetIndex;
                    currentSheetName = sheetName;
                    rowList = new ArrayList<>();
                }
                if (CollectionUtils.isNotEmpty(row)) {
                    Object[] cellArray = new Object[row.size()];
                    int j = 0;
                    boolean notEmpty = false;
                    for (Object cell : row) {
                        if (null == cell || StringUtils.isBlank(cell.toString())) {
                            cell = "";
                        } else {
                            notEmpty = true;
                        }
                        cellArray[j] = cell.toString();
                        j++;
                    }
                    int size = rowList.size();
                    while (size < (rowIndex)) {
                        rowList.add(new Object[]{});
                        size++;
                    }
                    if (notEmpty) {
                        rowList.add(cellArray);
                    } else {
                        rowList.add(new Object[]{});
                    }
                }
            }

            @Override
            public void handleCell(int sheetIndex, String sheetName, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {
            }
        };
    }
}
