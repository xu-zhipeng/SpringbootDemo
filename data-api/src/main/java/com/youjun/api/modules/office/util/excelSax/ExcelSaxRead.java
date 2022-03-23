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

    public Map<String, Object[][]> read(InputStream in) {
        in = IoUtil.toMarkSupportStream(in);
        RowHandlerImpl rowHandler = new RowHandlerImpl();
        ExcelSaxReader<?> reader = (ExcelFileUtil.isXlsx(in) ? new Excel07SaxReader(rowHandler) : new Excel03SaxReader(rowHandler));
        reader.read(in, -1);
        return rowHandler.dataArray;
    }

    /**
     * Sax解析excel 行处理
     *
     * @return
     */
    class RowHandlerImpl implements RowHandler {
        private int currentSheetIndex = 0;
        private String currentSheetName = null;
        private Map<String, Object[][]> dataArray = new HashMap<>();
        private List<Object[]> rowList = new ArrayList<>();

        @Override
        public void handle(int sheetIndex, String sheetName, long rowIndex, List<Object> row) {
            this.currentSheetIndex = sheetIndex;
            this.currentSheetName = sheetName;
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
                int size = this.rowList.size();
                while (size < (rowIndex)) {
                    this.rowList.add(new Object[]{});
                    size++;
                }
                if (notEmpty) {
                    this.rowList.add(cellArray);
                } else {
                    this.rowList.add(new Object[]{});
                }
            }
        }

        @Override
        public void handleCell(int sheetIndex, String sheetName, long rowIndex, int cellIndex, Object value, CellStyle xssfCellStyle) {

        }

        @Override
        public void doAfterAllAnalysed() {
            //最后一个行遍历结束 push row
            Object[] rowArray = this.rowList.toArray();
            Object[][] sheetArray = new Object[this.rowList.size()][];
            System.arraycopy(rowArray, 0, sheetArray, 0, rowArray.length);
            this.dataArray.put(this.currentSheetName, sheetArray);
            this.rowList = new ArrayList<>();
        }
    }
}
