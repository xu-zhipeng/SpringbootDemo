package com.youjun.api.modules.office.util.excelSax;

import cn.hutool.poi.excel.sax.ExcelSaxReader;
import com.youjun.common.util.StringUtils;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 由于hutool的Excel03SaxReader 无法获取sheetName 所以，参考它拷贝过来进行重写，大部分依赖，已经手动去除掉了，差异可能较大
 * </p>
 *
 * @author kirk
 * @since 2022/3/15
 */
public class Excel03SaxReader implements HSSFListener, ExcelSaxReader<Excel03SaxReader> {
    private final boolean isOutputFormulaValues = true;
    private SheetRecordCollectingListener workbookBuildingListener;
    private HSSFWorkbook stubWorkbook;
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;
    private final List<BoundSheetRecord> boundSheetRecords = new ArrayList();
    private boolean isOutputNextStringRecord;
    private List<Object> rowCellList = new ArrayList();
    private int rid = -1;
    private int curRid = -1;
    private String sheetName = null;
    private final RowHandler rowHandler;
    private final DataFormatter formatter = new DataFormatter();

    public Excel03SaxReader(RowHandler rowHandler) {
        this.rowHandler = rowHandler;
    }

    @Override
    public Excel03SaxReader read(File file, String idOrRid) throws RuntimeException {
        try {
            return this.read(new POIFSFileSystem(file), idOrRid);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    @Override
    public Excel03SaxReader read(InputStream excelStream, String idOrRid) throws RuntimeException {
        try {
            return this.read(new POIFSFileSystem(excelStream), idOrRid);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Excel03SaxReader read(POIFSFileSystem fs, String id) throws RuntimeException {
        this.rid = this.getSheetIndex(id);
        this.formatListener = new FormatTrackingHSSFListener(new MissingRecordAwareHSSFListener(this));
        HSSFRequest request = new HSSFRequest();
        if (isOutputFormulaValues) {
            request.addListenerForAllRecords(this.formatListener);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        HSSFEventFactory factory = new HSSFEventFactory();

        try {
            factory.processWorkbookEvents(request, fs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != fs) {
                try {
                    fs.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return this;
    }

    public int getSheetIndex() {
        return this.rid;
    }

    public String getSheetName() {
        return this.boundSheetRecords.size() > this.rid ? ((BoundSheetRecord) this.boundSheetRecords.get(this.rid > -1 ? this.rid : this.curRid)).getSheetname() : null;
    }

    /**
     * 实现 POI HSSFListener 接口的processRecord方法，来操作数据
     *
     * @param record
     */
    @Override
    public void processRecord(Record record) {
        if (this.rid <= -1 || this.curRid <= this.rid) {
            if (record instanceof BoundSheetRecord) {
                this.boundSheetRecords.add((BoundSheetRecord) record);
            } else if (record instanceof SSTRecord) {
                this.sstRecord = (SSTRecord) record;
            } else if (record instanceof BOFRecord) {
                //开始处理每个sheet
                BOFRecord bofRecord = (BOFRecord) record;
                if (bofRecord.getType() == BOFRecord.TYPE_WORKSHEET) {
                    //如果有需要，则建立子工作簿
                    if (this.workbookBuildingListener != null && this.stubWorkbook == null) {
                        this.stubWorkbook = this.workbookBuildingListener.getStubHSSFWorkbook();
                    }

                    ++this.curRid;
                    this.sheetName = this.getSheetName();
                }
            } else if (record instanceof EOFRecord) {
                this.processLastCellSheet();
            } else if (this.isProcessCurrentSheet()) {
                if (record instanceof MissingCellDummyRecord) {
                    MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
                    this.addToRowCellList(mc);
                } else if (record instanceof LastCellOfRowDummyRecord) {
                    this.processLastCell((LastCellOfRowDummyRecord) record);
                } else {
                    this.processCellValue(record);
                }
            }

        }
    }

    private void addToRowCellList(MissingCellDummyRecord record) {
        this.addToRowCellList(record.getRow(), record.getColumn(), "");
    }

    private void addToRowCellList(CellValueRecordInterface record, Object value) {
        this.addToRowCellList(record.getRow(), record.getColumn(), value);
    }

    private void addToRowCellList(int row, int column, Object value) {
        while (column > this.rowCellList.size()) {
            this.rowCellList.add("");
            this.rowHandler.handleCell(this.curRid, this.sheetName, (long) row, this.rowCellList.size() - 1, value, (CellStyle) null);
        }

        this.rowCellList.add(column, value);
        this.rowHandler.handleCell(this.curRid, this.sheetName, (long) row, column, value, (CellStyle) null);
    }

    private void processCellValue(Record record) {
        Object value = null;
        switch (record.getSid()) {
            case FormulaRecord.sid:
                FormulaRecord formulaRec = (FormulaRecord) record;
                if (isOutputFormulaValues) {
                    if (Double.isNaN(formulaRec.getValue())) {
                        this.isOutputNextStringRecord = true;
                    } else {
                        value = HSSFFormulaParser.toFormulaString(stubWorkbook, formulaRec.getParsedExpression());
                    }
                } else {
                    value = this.getNumberOrDateValue(formulaRec, formulaRec.getValue(), this.formatListener);
                }
                this.addToRowCellList(formulaRec, value);
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                if (null != this.sstRecord) {
                    value = this.sstRecord.getString(lsrec.getSSTIndex()).toString();
                }

                this.addToRowCellList(lsrec, null != value ? value : "");
                break;
            case BlankRecord.sid:
                this.addToRowCellList((BlankRecord) record, "");
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                value = this.getNumberOrDateValue(numrec, numrec.getValue(), this.formatListener);
                this.addToRowCellList(numrec, value);
                break;
            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;
                value = lrec.getValue();
                this.addToRowCellList(lrec, value);
                break;
            case BoolErrRecord.sid:
                BoolErrRecord berec = (BoolErrRecord) record;
                this.addToRowCellList(berec, berec.getBooleanValue());
                break;
            case StringRecord.sid:
                if (this.isOutputNextStringRecord) {
                    this.isOutputNextStringRecord = false;
                }
        }

    }

    private void processLastCell(LastCellOfRowDummyRecord lastCell) {
        this.rowHandler.handle(this.curRid, this.sheetName, (long) lastCell.getRow(), this.rowCellList);
        this.rowCellList = new ArrayList(this.rowCellList.size());
    }

    private void processLastCellSheet() {
        this.rowHandler.doAfterAllAnalysed();
    }

    private boolean isProcessCurrentSheet() {
        return this.rid < 0 || this.curRid == this.rid;
    }

    private int getSheetIndex(String idOrRidOrSheetName) {
        if (StringUtils.isBlank(idOrRidOrSheetName)) {
            throw new IllegalArgumentException("id or rid or sheetName must be not blank!");
        }
        if (idOrRidOrSheetName.startsWith("rId")) {
            return Integer.parseInt(idOrRidOrSheetName.substring("rId".length()));
        } else {
            try {
                return Integer.parseInt(idOrRidOrSheetName);
            } catch (NumberFormatException var4) {
                throw new IllegalArgumentException("Invalid sheet id: " + idOrRidOrSheetName);
            }
        }
    }

    /**
     * 该方法 从 hutool cn.hutool.poi.excel.sax.ExcelSaxUtil 拷过来修改
     *
     * @param cell
     * @param value
     * @param formatListener
     * @return
     */
    public Object getNumberOrDateValue(CellValueRecordInterface cell, double value, FormatTrackingHSSFListener formatListener) {
        int formatIndex = formatListener.getFormatIndex(cell);
        String formatString = formatListener.getFormatString(cell);
        if (isDateFormat(formatIndex, formatString)) {
            /**
             * 确定是日期数据会存在一个问题
             * 注意：formatRawCellContents方法内部也会判断是否日期，日期则调用DateUtil.getJavaDate(value, false);
             * 问题是：年月日的等格式的日期,这个DateUtil.isADateFormat方法判断不出来是日期，所以导致转换数据有问题
             * 有两个方法解决上面的问题，
             * 其一：修改formatString，修改成我们想要的格式(并且formatRawCellContents内部的isADateFormat方法能够判断为true) 然后调用formatRawCellContents方法
             * 其二：自定义isADateFormat方法对其做增强,然后调用 DateUtil.getJavaDate(value, false);方法获取java Date类型时间
             */
            formatString = getFormatString(formatString);
            return this.formatter.formatRawCellContents(value, formatIndex, formatString);
        } else {
            return this.formatter.formatRawCellContents(value, formatIndex, formatString);
        }
    }

    private static final int[] customFormats = new int[]{28, 30, 31, 32, 33, 55, 56, 57, 58};
    private static final CharSequence[] charSequences = new CharSequence[]{"周", "星期", "aa"};

    private boolean isDateFormat(int formatIndex, String formatString) {
        for (int index : customFormats) {
            if (index == formatIndex) {
                return true;
            }
        }
        for (CharSequence c : charSequences) {
            if (formatString.contains(c)) {
                return true;
            }
        }
        return DateUtil.isADateFormat(formatIndex, formatString);
    }

    /**
     * 根据office原始格式修改成我们想要的日期格式
     *
     * @param formatString
     * @return
     */
    private String getFormatString(String formatString) {
        switch (formatString) {
            case "m/d/yy":
                formatString = "yyyy-MM-dd";
                break;
            case "m/d/yyyy":
                formatString = "yyyy-MM-dd";
                break;
            case "yyyy/mm/dd":
                formatString = "yyyy-MM-dd";
                break;
            case "yyyy/m/d":
                formatString = "yyyy-MM-dd";
                break;
            case "yyyy\\-mm\\-dd":
                formatString = "yyyy-MM-dd";
                break;
            case "yyyy\\-m\\-d":
                formatString = "yyyy-MM-dd";
                break;
            case "yyyy\\-mm\\-dd\\ hh:mm:ss":
                formatString = "yyyy-MM-dd hh:mm:ss";
                break;
            case "reserved-0x1F":
                //yyyy"年"m"月"d"日"之类的格式无法识别 进入这里
                formatString = "yyyy-MM-dd hh:mm:ss";
                break;
            default:
                formatString = "yyyy-MM-dd hh:mm:ss";
        }
        return formatString;
    }

    /**
     * this.formatter.formatRawCellContents(value, formatIndex, formatString) 里面也会调用 DateUtil.getJavaDate(value, false)
     * ,但前提是DateUtil.isADateFormat判断为true
     *
     * @param value
     * @return
     */
    private Date getDateValue(double value) {
        return DateUtil.getJavaDate(value, false);
    }
}
