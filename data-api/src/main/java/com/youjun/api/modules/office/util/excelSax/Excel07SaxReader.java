package com.youjun.api.modules.office.util.excelSax;

import cn.hutool.poi.excel.sax.ExcelSaxReader;
import com.youjun.common.util.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 由于hutool的Excel07SaxReader 无法获取sheetName 所以，参考它拷贝过来进行重写，大部分依赖，已经手动去除掉了，差异可能较大
 * </p>
 *
 * @author kirk
 * @since 2022/3/14
 */
public class Excel07SaxReader extends DefaultHandler implements ExcelSaxReader<Excel07SaxReader> {
    protected StylesTable stylesTable;
    protected SharedStringsTable sharedStringsTable;
    protected int sheetIndex;
    protected String sheetName;
    protected int index;
    private int curCell;
    private CellDataType cellDataType;
    private long rowNumber;
    private String curCoordinate;
    private ElementName curElementName;
    private String preCoordinate;
    private String maxCellCoordinate;
    private XSSFCellStyle xssfCellStyle;
    private short numFmtIndex;
    private String numFmtString;
    private final DataFormatter formatter = new DataFormatter();
    private boolean isInSheetData;
    private StringBuilder lastContent = new StringBuilder();
    private StringBuilder lastFormula = new StringBuilder();
    private List<Object> rowCellList = new ArrayList();
    protected RowHandler rowHandler;

    public Excel07SaxReader(RowHandler rowHandler) {
        this.rowHandler = rowHandler;
    }

    public void setRowHandler(RowHandler rowHandler) {
        this.rowHandler = rowHandler;
    }

    @Override
    public Excel07SaxReader read(File file, int rid) throws RuntimeException {
        return this.read(file, "rId" + rid);
    }

    @Override
    public Excel07SaxReader read(File file, String idOrRidOrSheetName) throws RuntimeException {
        try {
            return this.readSheets(new FileInputStream(file), idOrRidOrSheetName);
        } catch (FileNotFoundException var4) {
            throw new RuntimeException(var4);
        }
    }

    @Override
    public Excel07SaxReader read(InputStream in, int rid) throws RuntimeException {
        return this.readSheets(in, "rId" + rid);
    }

    @Override
    public Excel07SaxReader read(InputStream in, String idOrRidOrSheetName) throws RuntimeException {
        return this.readSheets(in, idOrRidOrSheetName);
    }

    private Excel07SaxReader readSheets(InputStream in, String idOrRidOrSheetName) throws RuntimeException {
        Throwable throwable = null;
        OPCPackage opcPackage = null;
        InputStream sheetInputStream = null;

        try {
            opcPackage = OPCPackage.open(in);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            this.stylesTable = xssfReader.getStylesTable();
            this.sharedStringsTable = xssfReader.getSharedStringsTable();

            this.sheetIndex = this.getSheetIndex(idOrRidOrSheetName);
            if (this.sheetIndex > -1) {
                sheetInputStream = xssfReader.getSheet("rId" + (this.sheetIndex + 1));
                XMLReader xmlReader = XMLHelper.newXMLReader();
                xmlReader.setContentHandler(this);
                xmlReader.parse(new InputSource(sheetInputStream));
                this.rowHandler.doAfterAllAnalysed();
            } else {
                this.sheetIndex = -1;
                XSSFReader.SheetIterator sheetInputStreams = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

                while (sheetInputStreams.hasNext()) {
                    this.index = 0;
                    ++this.sheetIndex;
                    sheetInputStream = sheetInputStreams.next();
                    this.sheetName = sheetInputStreams.getSheetName();
                    XMLReader xmlReader = XMLHelper.newXMLReader();
                    xmlReader.setContentHandler(this);
                    xmlReader.parse(new InputSource(sheetInputStream));
                    this.rowHandler.doAfterAllAnalysed();
                }
            }
        } catch (RuntimeException e) {
            throwable = e;
            throw e;
        } catch (Exception e) {
            throwable = e;
            throw new RuntimeException(e);
        } finally {
            if (null != sheetInputStream) {
                try {
                    sheetInputStream.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (opcPackage != null) {
                if (throwable != null) {
                    try {
                        opcPackage.close();
                    } catch (Throwable e) {
                        throwable.addSuppressed(e);
                    }
                } else {
                    try {
                        opcPackage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return this;
    }

    private int getSheetIndex(String idOrRidOrSheetName) {
        if (idOrRidOrSheetName.startsWith("rId")) {
            return Integer.parseInt(idOrRidOrSheetName.substring("rId".length()));
        } else {
            try {
                int sheetIndex = Integer.parseInt(idOrRidOrSheetName);
                return sheetIndex;
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid rId or id or sheetName: " + idOrRidOrSheetName);
            }
        }

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("sheetData".equals(qName)) {
            this.isInSheetData = true;
        } else if (this.isInSheetData) {
            ElementName name = ElementName.of(qName);
            this.curElementName = name;
            if (null != name) {
                switch (name) {
                    case row:
                        this.startRow(attributes);
                        break;
                    case c:
                        this.startCell(attributes);
                }
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("sheetData".equals(qName)) {
            this.isInSheetData = false;
        } else if (this.isInSheetData) {
            this.curElementName = null;
            if (ElementName.c.match(qName)) {
                this.endCell();
            } else if (ElementName.row.match(qName)) {
                this.endRow();
            }

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (this.isInSheetData) {
            ElementName elementName = this.curElementName;
            if (null != elementName) {
                switch (elementName) {
                    case v:
                        this.lastContent.append(ch, start, length);
                        break;
                    case f:
                        this.lastFormula.append(ch, start, length);
                }
            } else {
                this.lastContent.append(ch, start, length);
            }

        }
    }

    private void startRow(Attributes attributes) {
        String rValue = AttributeName.r.getValue(attributes);
        if (null != rValue) {
            this.rowNumber = Long.parseLong(rValue) - 1L;
        }

    }

    private void startCell(Attributes attributes) {
        String tempCurCoordinate = AttributeName.r.getValue(attributes);
        if (this.preCoordinate == null) {
            this.preCoordinate = String.valueOf('@');
        } else {
            this.preCoordinate = this.curCoordinate;
        }

        this.curCoordinate = tempCurCoordinate;
        this.setCellType(attributes);
        //清空
        this.lastContent.delete(0, this.lastContent.length());
        this.lastFormula.delete(0, this.lastFormula.length());
    }

    private void endRow() {
        if (this.index == 0) {
            this.maxCellCoordinate = this.curCoordinate;
        }

        if (this.maxCellCoordinate != null) {
            this.fillBlankCell(this.curCoordinate, this.maxCellCoordinate, true);
        }

        this.rowHandler.handle(this.sheetIndex, this.sheetName, this.rowNumber, this.rowCellList);
        this.rowCellList = new ArrayList(this.curCell + 1);
        ++this.index;
        this.curCell = 0;
        this.curCoordinate = null;
        this.preCoordinate = null;
    }

    private void endCell() {
        this.fillBlankCell(this.preCoordinate, this.curCoordinate, false);
        String contentStr = this.lastContent.toString().trim();
        Object value = getDataValue(contentStr);
        if (null != this.lastFormula && this.lastFormula.length() > 0) {
            value = new FormulaCellValue(this.lastFormula.toString().trim(), value);
        }

        this.addCellValue(this.curCell++, value);
    }

    private void addCellValue(int index, Object value) {
        this.rowCellList.add(index, value);
        this.rowHandler.handleCell(this.sheetIndex, this.sheetName, this.rowNumber, index, value, this.xssfCellStyle);
    }

    private void fillBlankCell(String preCoordinate, String curCoordinate, boolean isEnd) {
        if (!curCoordinate.equals(preCoordinate)) {
            int len = countNullCell(preCoordinate, curCoordinate);
            if (isEnd) {
                ++len;
            }

            while (len-- > 0) {
                this.addCellValue(this.curCell++, "");
            }
        }

    }

    private void setCellType(Attributes attributes) {
        this.numFmtString = "";
        this.cellDataType = CellDataType.of(AttributeName.t.getValue(attributes));
        if (null != this.stylesTable) {
            String xfIndexStr = AttributeName.s.getValue(attributes);
            if (null != xfIndexStr) {
                this.xssfCellStyle = this.stylesTable.getStyleAt(Integer.parseInt(xfIndexStr));
                this.numFmtIndex = this.xssfCellStyle.getDataFormat();
                this.numFmtString = Optional.ofNullable(this.xssfCellStyle.getDataFormatString()).orElse(BuiltinFormats.getBuiltinFormat(this.numFmtIndex));
                if (CellDataType.NUMBER == this.cellDataType && isDateFormat(this.numFmtIndex, this.numFmtString)) {
                    this.cellDataType = CellDataType.DATE;
                    if(!DateUtil.isADateFormat(this.numFmtIndex, this.numFmtString)){
                        this.numFmtString = getFormatString(this.numFmtString);
                    }
                }
            }
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

    private Object getDataValue(String value) {
        if (null == value) {
            return null;
        } else {
            if (null == this.cellDataType) {
                this.cellDataType = CellDataType.NULL;
            }

            Object result;
            switch (this.cellDataType) {
                case BOOL:
                    result = value.charAt(0) != '0';
                    break;
                case ERROR:
                    result = String.format("\\\"ERROR: %s ", value);
                    break;
                case FORMULA:
                    result = String.format("\"%s\"", value);
                    break;
                case INLINESTR:
                    result = (new XSSFRichTextString(value)).toString();
                    break;
                case SSTINDEX:
                    try {
                        int index = Integer.parseInt(value);
                        result = this.sharedStringsTable.getItemAt(index).getString();
                    } catch (NumberFormatException var8) {
                        result = value;
                    }
                    break;
                case NUMBER:
                    try {
                        result = this.formatter.formatRawCellContents(Double.parseDouble(value), this.numFmtIndex, this.numFmtString).trim();
                    } catch (NumberFormatException var7) {
                        result = value;
                    }
                    break;
                case DATE:
                    try {
                        result = this.formatter.formatRawCellContents(Double.parseDouble(value), this.numFmtIndex, this.numFmtString);
                    } catch (Exception var6) {
                        result = value;
                    }
                    break;
                default:
                    result = value;
            }

            return result;
        }
    }

    private int countNullCell(String preRef, String ref) {
        Objects.requireNonNull(ref, "");
        String preXfd = Optional.ofNullable(preRef).orElse("@").replaceAll("\\d+", "");
        String xfd = Optional.ofNullable(ref).orElse("@").replaceAll("\\d+", "");
        preXfd = StringUtils.fillBefore(preXfd, '@', 3);
        xfd = StringUtils.fillBefore(xfd, '@', 3);
        char[] preLetter = preXfd.toCharArray();
        char[] letter = xfd.toCharArray();
        int res = (letter[0] - preLetter[0]) * 26 * 26 + (letter[1] - preLetter[1]) * 26 + (letter[2] - preLetter[2]);
        return res - 1;
    }

    /**
     * 根据office原始格式修改成我们想要的日期格式
     *
     * @param formatString
     * @return
     */
    private String getFormatString(String formatString) {
        switch (formatString) {
            case "reserved-0x1F":
                //yyyy"年"m"月"d"日"之类的格式无法识别 进入这里
                formatString = "yyyy-MM-dd";
                break;
            default:
                formatString = "yyyy-MM-dd hh:mm:ss";
        }
        return formatString;
    }

    private enum ElementName {
        row,
        c,
        v,
        f;

        private ElementName() {
        }

        public boolean match(String elementName) {
            return this.name().equals(elementName);
        }

        public static ElementName of(String elementName) {
            try {
                return valueOf(elementName);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private enum CellDataType {
        BOOL("b"),
        ERROR("e"),
        FORMULA("formula"),
        INLINESTR("inlineStr"),
        SSTINDEX("s"),
        NUMBER(""),
        DATE("m/d/yy"),
        NULL("");

        private final String name;

        private CellDataType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static CellDataType of(String name) {
            if (null == name) {
                return NUMBER;
            } else if (BOOL.name.equals(name)) {
                return BOOL;
            } else if (ERROR.name.equals(name)) {
                return ERROR;
            } else if (INLINESTR.name.equals(name)) {
                return INLINESTR;
            } else if (SSTINDEX.name.equals(name)) {
                return SSTINDEX;
            } else {
                return FORMULA.name.equals(name) ? FORMULA : NULL;
            }
        }
    }

    private enum AttributeName {
        r,
        s,
        t;

        private AttributeName() {
        }

        public boolean match(String attributeName) {
            return this.name().equals(attributeName);
        }

        public String getValue(Attributes attributes) {
            return attributes.getValue(this.name());
        }
    }

}

/**
 * <p>
 * 当单元格 包含公式时，存储值和公式
 * </p>
 *
 * @author kirk
 * @since 2022/3/22
 */
class FormulaCellValue {
    String formula;
    Object result;

    public FormulaCellValue(String formula) {
        this(formula, (Object) null);
    }

    public FormulaCellValue(String formula, Object result) {
        this.formula = formula;
        this.result = result;
    }

    public String getValue() {
        return this.formula;
    }

    public Object getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return this.getResult().toString();
    }
}