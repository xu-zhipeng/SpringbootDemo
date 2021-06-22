package com.youjun.api.modules.office.controller;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.youjun.api.modules.office.model.BussinessLitigationSourceBasicEntity;
import com.youjun.api.modules.office.model.Word2003NamespaceContext;
import com.youjun.api.modules.office.model.Word2007NamespaceContext;
import com.youjun.common.api.CommonResult;
import com.youjun.common.util.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/4/7
 */
@Slf4j
@RestController
@RequestMapping("/office")
public class OfficeController {

    @ApiOperation("生成word报告")
    @GetMapping("/exportWord2003")
    public CommonResult exportWord2003() throws IOException {
        String telephone = "□";
        String faceToFace = "□";
        String other_feedback = "□";
        String satisfied = "□";
        String basicUnderstanding = "□";
        String dissatisfied = "□";
        String other_satisfaction = "□";
        Integer handleOpinion = 1;
        switch (handleOpinion) {
            case 1:
                telephone = "√";
                break;
            case 2:
                faceToFace = "√";
                break;
            case 3:
                other_feedback = "√";
                break;
            default:
        }
        Integer serverOpinion = 1;
        switch (serverOpinion) {
            case 1:
                satisfied = "√";
                break;
            case 2:
                basicUnderstanding = "√";
                break;
            case 3:
                dissatisfied = "√";
                break;
            case 4:
                other_satisfaction = "√";
            default:
        }
        FileInputStream inputStream = new FileInputStream("D:\\template\\信访代办导出模板.doc");
        HWPFDocument document = new HWPFDocument(inputStream);
        Range range = document.getRange();
        range.replaceText("${letterNumber}", "20210409");
        range.replaceText("${eventDate}", "2021-04-09");
        range.replaceText("${contactName}", "林泽");
        range.replaceText("${reflectionPersonNum}", "1");
        range.replaceText("${reflectionAddr}", "杭州市余杭区仓前街道");
        range.replaceText("${reflectionTel}", "18787234000");
        range.replaceText("${eventContent}", "反映内容及诉求1111111111111111");
        range.replaceText("${reflectionPerson}", "许志鹏");
        range.replaceText("${contactPhone}", "18700234000");
        range.replaceText("${deptName}", "街道党建办");
        range.replaceText("${leaderOpinion}", "办理结果11111");
        range.replaceText("${dueDate}", "2021-04-09");
        range.replaceText("${telephone}", telephone);
        range.replaceText("${faceToFace}", faceToFace);
        range.replaceText("${other_feedback}", other_feedback);
        range.replaceText("${satisfied}", satisfied);
        range.replaceText("${basicUnderstanding}", basicUnderstanding);
        range.replaceText("${dissatisfied}", dissatisfied);
        range.replaceText("${other_satisfaction}", other_satisfaction);
        //写入文档
        try (FileOutputStream outputStream = new FileOutputStream("D:\\out.doc")) {
            document.write(outputStream);
        }
        return CommonResult.success(null);
    }

    @ApiOperation("生成word报告")
    @GetMapping("/exportWord2007")
    public CommonResult exportWord2007() throws IOException {
        //创建文档
        XWPFDocument document = new XWPFDocument();
        //段落XWPFParagraph
        XWPFParagraph paragraph;
        //基本元素XWPFRun
        XWPFRun run;
        //表格XWPFTable
        XWPFTable table;
        //创建 新段落
        paragraph = document.createParagraph();
        // 设置段落格式
        // 对齐方式
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        // 边框
        paragraph.setBorderBottom(Borders.DOUBLE);
        paragraph.setBorderTop(Borders.DOUBLE);
        paragraph.setBorderRight(Borders.DOUBLE);
        paragraph.setBorderLeft(Borders.DOUBLE);
        paragraph.setBorderBetween(Borders.SINGLE);
        // 段落末尾创建XWPFRun
        run = paragraph.createRun();
        run.setText("为这个段落追加文本");
        //创建新表格
        table = document.createTable(10, 4);
        table.getRow(1).getCell(1).setText("代办编号");
        //写入文档
        try (FileOutputStream outputStream = new FileOutputStream("D:\\out.docx")) {
            document.write(outputStream);
        }
        return CommonResult.success(null);
    }

    @RequestMapping("readWordXML")
    public CommonResult readWordXML() throws IOException {
        //加载文档
//        String filePath = "D:\\template\\HZSDH20210922243（公开电话）-2003.xml";
        String filePath = "D:\\template\\HZSDH20210922243（公开电话）.doc";
//        String filePath = "D:\\template\\document.xml";
        //WordExtractor
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            WordExtractor document = new WordExtractor(inputStream);
            if (document != null) {
                log.info("WordExtractor success");
            }
        } catch (Exception e) {
            log.error("WordExtractor:" + e.getMessage());
        }
        //HWPFDocument
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            HWPFDocument document = new HWPFDocument(inputStream);
            if (document != null) {
                log.info("HWPFDocument success");
            }
        } catch (Exception e) {
            log.error("HWPFDocument:" + e.getMessage());
        }
        //XWPFDocument
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(inputStream);
            if (document != null) {
                log.info("XWPFDocument success");
            }
        } catch (Exception e) {
            log.error("XWPFDocument:" + e.getMessage());
        }
        //HSSFWorkbook
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            if (workbook != null) {
                log.info("HSFWorSkbook success");
            }
        } catch (Exception e) {
            log.error("HSSFWorkbook:" + e.getMessage());
        }
        //XSSFWorkbook
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            if (workbook != null) {
                log.info("XSSFWorkbook success");
            }
        } catch (Exception e) {
            log.error("XSSFWorkbook:" + e.getMessage());
        }
        //readXML
        try {
            //加载文档
            FileInputStream inputStream = new FileInputStream(filePath);
            /*//创建解析工厂DocumentBuilderFactory
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl", (ClassLoader)null);
            //指定DocumentBuilder实例
            DocumentBuilder domBuilder=builderFactory.newDocumentBuilder();
            //创建Document对象
            org.w3c.dom.Document document=domBuilder.parse(new InputSource(file.getInputStream()));*/
            org.w3c.dom.Document document = XmlUtil.readXML(inputStream);
            if (document != null) {
                //判断版本 03 07
                org.w3c.dom.Element w_wordDocument = (org.w3c.dom.Element) XmlUtil.getByXPath("//w:wordDocument", document, XPathConstants.NODE, new Word2003NamespaceContext());
                org.w3c.dom.Element pkg_package = (org.w3c.dom.Element) XmlUtil.getByXPath("//pkg:package", document, XPathConstants.NODE, new Word2007NamespaceContext());
                String wordBodyXpath = null;
                NamespaceContext wordNamespaceContext = null;
                if (w_wordDocument != null) {
                    //03
                    wordBodyXpath = "//w:body/wx:sect";
                    wordNamespaceContext = new Word2003NamespaceContext();
                } else if (pkg_package != null) {
                    //07
                    wordBodyXpath = "//w:body";
                    wordNamespaceContext = new Word2007NamespaceContext();
                } else {
                    throw new RuntimeException("文件格式错误");
                }
                String titleXpath = wordBodyXpath + "/w:p[1]/w:r[1]/w:t[1]";
                org.w3c.dom.Element element = (org.w3c.dom.Element) XmlUtil.getByXPath(titleXpath, document, XPathConstants.NODE, wordNamespaceContext);
                String title = element.getTextContent();
                String tableXpath = wordBodyXpath + "/w:tbl[1]";
                //编号
                String letterNumber = getCellValueFromTable(wordNamespaceContext, tableXpath, document, 0, 1);
                if (!org.springframework.util.StringUtils.hasText(letterNumber)) {
                    throw new RuntimeException("编号 不能为空");
                }


                log.info("readXML success");
            }
        } catch (Exception e) {
            log.error("readXML:" + e.getMessage());
        }
        //Dom4j
        try {
            SAXReader reader = new SAXReader();
            FileInputStream inputStream = new FileInputStream(filePath);
            Document document = reader.read(inputStream);
            if (document != null) {
                //获取根节点元素对象
                Element root = document.getRootElement();
                Element wordBody = null;
                if ("wordDocument".equalsIgnoreCase(root.getName())) {
                    //word.xml 2003
                    List<Node> sect = document.selectNodes("sect");
                    wordBody = (Element) sect.get(0);
                } else if ("package".equalsIgnoreCase(root.getName())) {
                    //word.xml 2007
                    //遍历
                    Iterator<Element> iterator = root.elementIterator();
                    while (iterator.hasNext() && wordBody == null) {
                        Element element = iterator.next();
                        //首先获取当前节点的所有属性节点
                        List<Attribute> attributes = element.attributes();
                        if (CollectionUtils.isNotEmpty(attributes)) {
                            for (Attribute attribute : attributes) {
                                //判断获取 word.xml 的内容部分part标签
                                if ("name".equalsIgnoreCase(Optional.ofNullable(attribute.getName()).orElse(""))
                                        && "/word/document.xml".equalsIgnoreCase(Optional.ofNullable(attribute.getValue()).orElse(""))) {
                                    wordBody = element;
                                    break;
                                }
                            }
                        }

                    }
                }
                listNodes(wordBody);
                log.info("readXML success");
            }
        } catch (Exception e) {
            log.error("readXML:" + e.getMessage());
        }
        System.out.println("结束");
        return CommonResult.success(null);
    }

    //w3c dom xml
    public String getCellValueFromTable(NamespaceContext wordNamespaceContext, String tableXpath, org.w3c.dom.Document document, int rowIndex, int columnIndex) throws XPathExpressionException {
        //索引值从1开始  参数从0开始  需加1
        rowIndex++;
        columnIndex++;
        String runsXpath = tableXpath + "/w:tr[" + rowIndex + "]/w:tc[" + columnIndex + "]/w:p[1]/w:r/w:t[1]";
        XPath xPath = XPathFactory.newInstance().newXPath();
        //设置 xml命名空间uri
        xPath.setNamespaceContext(wordNamespaceContext);
        NodeList nodeList = null;
        if (document instanceof InputSource) {
            nodeList = (NodeList) xPath.evaluate(runsXpath, (InputSource) document, XPathConstants.NODESET);
        } else {
            nodeList = (NodeList) xPath.evaluate(runsXpath, document, XPathConstants.NODESET);
        }
        //hutool封装方法
        //Element paragraphsElement = (Element) XmlUtil.getByXPath(valueXpath, document, XPathConstants.NODESET, new WordNamespaceContext());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; null != nodeList && i < nodeList.getLength(); i++) {
            org.w3c.dom.Element run = (org.w3c.dom.Element) nodeList.item(i);
            buffer.append(run.getTextContent());
        }
        return StringUtils.isNotBlank(buffer) ? buffer.toString() : "";
    }

    //遍历当前节点下的所有节点
    public void listNodes(Element node) {
        System.out.print("当前节点的名称：" + node.getName());
        //首先获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        //遍历属性节点
        System.out.print(",属性：{");
        boolean isFirst = true;
        for (Attribute attribute : list) {
            if (isFirst) {
                isFirst = false;
                System.out.print(attribute.getName() + ":" + attribute.getValue());
            } else {
                System.out.print("," + attribute.getName() + ":" + attribute.getValue());
            }
        }
        System.out.print("}\n");
        //如果当前节点内容不为空，则输出
        if (!(node.getTextTrim().equals(""))) {
            System.out.println("        文本{" + node.getName() + "：" + node.getText() + "}");
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<org.dom4j.Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            listNodes(e);
        }
    }

    @RequestMapping("readWord2003")
    public CommonResult readWord2003() {
        try {
            //加载文档
            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）-2003.doc");
            HWPFDocument doc = new HWPFDocument(inputStream);
            Range range = doc.getRange();
            // 段落
            for (int i = 0; i < range.numParagraphs(); i++) {
                System.out.println("第" + i + "个段落：" + range.getParagraph(i).text());
            }
            //表格
            TableIterator tableIterator = new TableIterator(range);
            Table table;
            TableRow row;
            TableCell cell;
            int i = 0;
            while (tableIterator.hasNext()) {
                System.out.println("第" + (i++) + "个表格");
                table = tableIterator.next();
                for (int j = 0; j < table.numRows(); j++) {
                    System.out.println("        第" + j + "行");
                    row = table.getRow(j);
                    for (int k = 0; k < row.numCells(); k++) {
                        cell = row.getCell(k);
                        String value = cell.text();
                        value = value
                                .replace("\r", "")
                                .replace("\n", "")
                                .replace("\t", "")
                                .trim();
                        System.out.println("            第" + k + "列:" + value);
                    }
                }
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    @RequestMapping("readWord2007")
    public CommonResult readWord2007() {
        int i, j, k;
        try {
            //加载文档
            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）-2007.docx");
            XWPFDocument doc = new XWPFDocument(inputStream);
            // 段落
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            i = 0;
            for (XWPFParagraph paragraph : paragraphs) {
                System.out.println("第" + (i++) + "个段落：" + paragraph.getText());
            }
            // 表格
            List<XWPFTable> tables = doc.getTables();
            i = 0;
            for (XWPFTable table : tables) {
                System.out.println("第" + (i++) + "个表格");
                j = 0;
                for (XWPFTableRow row : table.getRows()) {
                    System.out.println("        第" + (j++) + "行");
                    k = 0;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String value = cell.getParagraphs().stream().map(XWPFParagraph::getText).collect(Collectors.joining());
                        System.out.println("            第" + (k++) + "列:" + value);

                    }
                }
            }
            // 图片
            List<XWPFPictureData> allPictures = doc.getAllPictures();
            // 页眉
            List<XWPFHeader> headerList = doc.getHeaderList();
            // 页脚
            List<XWPFFooter> footerList = doc.getFooterList();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    @RequestMapping("readWord20071")
    public CommonResult readWord20071() {
        try {
            //加载文档
            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）-2007.docx");
            XWPFDocument doc = new XWPFDocument(inputStream);
            //页眉页脚
            XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
            //获取页眉
            String header = headerFooterPolicy.getDefaultHeader().getText();
            System.out.println("***页眉 ***" + header);
            //获取页脚
            String footer = headerFooterPolicy.getDefaultFooter().getText();
            System.out.println("***页脚 ***" + header);
            //页眉边距
            CTDocument1 ctdoc = doc.getDocument();
            /*String top = ctdoc.getBody().getSectPr().getPgMar().getTop().toString();
            String bottom = ctdoc.getBody().getSectPr().getPgMar().getBottom().toString();
            String left = ctdoc.getBody().getSectPr().getPgMar().getLeft().toString();
            String right = ctdoc.getBody().getSectPr().getPgMar().getRight().toString();*/
            //获取标题
            List<XWPFParagraph> paras = doc.getParagraphs(); //将得到包含段落列表
            //获取标题
            List<Map<String, String>> list = getParagraph(paras.get(0));
            System.out.println("标题信息===" + list);
            //获取表格
            Iterator<IBodyElement> iter = doc.getBodyElementsIterator();
            while (iter.hasNext()) {
                // iter.next();
                IBodyElement element = iter.next();
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph para = (XWPFParagraph) element;
                    System.out.println("para===" + getParagraph(para));
                } else if (element instanceof XWPFTable) {
                    int row_count = 0;
                    XWPFTable table = (XWPFTable) element;
                    List<XWPFTableRow> xwpfTableRows = table.getRows();
                    row_count = xwpfTableRows.size();
                    ArrayList cell_count = new ArrayList();
                    int row_index = 1;
                    for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
                        List<XWPFTableCell> xwpfTableCells = xwpfTableRow.getTableCells();
                        cell_count.add(xwpfTableCells.size());
                        System.out.println("第" + row_index + "行");
                        int cell_index = 1;
                        for (XWPFTableCell xwpfTableCell : xwpfTableCells) {
                            //单元格是否被合并，合并了几个
                            CTDecimalNumber cellspan = xwpfTableCell.getCTTc().getTcPr().getGridSpan();
                            boolean gridspan = cellspan != null;
                            String gridspan_num = cellspan != null ? cellspan.getVal().toString() : "0";
                            List<XWPFParagraph> xwpfParagraphs = xwpfTableCell.getParagraphs();
                            XWPFParagraph paragraph = xwpfParagraphs.get(0);
                            System.out.println("第" + cell_index + "个单元格，合并标志：" + gridspan + ",合并个数:" + gridspan_num
                                    + "文字：" + getParagraph(paragraph));
                            cell_index++;
                        }
                        row_index++;
                    }
                    System.out.println("表格为：row_count===" + row_count + "行" + Collections.max(cell_count) + "列");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    @RequestMapping("downloadWord")
    public CommonResult downloadWord() {
        return CommonResult.success(null);
    }

    @RequestMapping("readExcel")
    public CommonResult readExcel() {
        try {
            //加载Excel
            InputStream inputStream = new FileInputStream("D:\\template\\诉源事件批量导入模板 .xlsx");
            //POI 读取Excel  WorkbookFactory 自动兼容 2003和2007
            Workbook workbook = WorkbookFactory.create(inputStream);
            //Sheet hssfSheet = workbook.getSheetAt(0);  //示意访问sheet

            // 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
            ExcelReader excelReader = ExcelUtil.getReader(inputStream, 0);
            // 可以加上表头验证 //获取表头
            List<List<Object>> read1 = excelReader.read(1, 1);
            // 3.读取第二行到最后一行数据
            List<List<Object>> read = excelReader.read(2, excelReader.getRowCount());
            System.out.println(new Gson().toJson(read1));
            System.out.println(new Gson().toJson(read));
            ArrayList<BussinessLitigationSourceBasicEntity> list = new ArrayList<>();
            //TODO 遍历数据
            for (List<Object> objects : read) {
                BussinessLitigationSourceBasicEntity entity = new BussinessLitigationSourceBasicEntity();
                //读取某行第一列数据
                //案件编码
                entity.setCaseCode(objects.get(0).toString());
                //调委会
                entity.setMediationCommittee(objects.get(1).toString());
                //调解工作室
                entity.setMediationStudio(objects.get(2).toString());
                //受理员
                entity.setAcceptor(objects.get(3).toString());
                //受理时间
                entity.setAcceptanceTime(objects.get(4).toString());
                //案件来源
//                entity.setSourceOfCase(objects.get(5).toString());
                //案件难度级别
                entity.setCaseDifficultyLevel(objects.get(6).toString());
                //纠纷类别
                entity.setTypeOfDispute(objects.get(7).toString());
                //行政划分
                entity.setAdministrativeDivision(objects.get(8).toString());
                //有无死亡
                entity.setDeathOrNot(objects.get(9).toString());
                //主办调解员
                entity.setHostMediator(objects.get(10).toString());
                //协办调解员
                entity.setCoMediator(objects.get(11).toString());
                //调解时间
                entity.setMediationTime(objects.get(12).toString());
                //调解书编号
                entity.setMediationNo(objects.get(13).toString());
                //调解结果
//                entity.setMediationResults(objects.get(14).toString());
                //调解协议金(元)
                entity.setMediationAgreementFee(objects.get(15).toString());
                //协议履行情况
//                entity.setAgreementPerformance(objects.get(16).toString());
                //当事人
                entity.setParty(objects.get(17).toString());
                //协议形式
                entity.setFormOfAgreement(objects.get(18).toString());
                //纠纷简要情况
                entity.setBriefInformation(objects.get(19).toString());
                //以奖代补评定
                entity.setAwardInsteadOfCompensation(objects.get(20).toString());
                //案件属性
                entity.setTheNatureOfTheCase(objects.get(21).toString());
                //涉及特殊群体情况
                entity.setCasesInvolvingSpecialGroups(objects.get(22).toString());
                //涉及农民工纠纷情况
                entity.setDisputesInvolvingMigrantWorkers(objects.get(23).toString());
                //纠纷转化情况
                entity.setDisputeTransformation(objects.get(24).toString());
                list.add(entity);
            }
            //TODO 插入数据
            //litigationSourceBasicService.saveBatch(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }


    @RequestMapping("downloadExcel")
    public CommonResult downloadExcel() {
        return CommonResult.success(null);
    }


    @RequestMapping("readExcelStyleTest")
    public CommonResult readExcelStyleTest() {
        try {
            //加载Excel
            InputStream inputStream = new FileInputStream("D:\\template\\A1001主协议.xlsx");
            //POI 读取Excel  WorkbookFactory 自动兼容 2003和2007
            Workbook workbook = WorkbookFactory.create(inputStream);
            //Sheet hssfSheet = workbook.getSheetAt(0);  //示意访问sheet
            Sheet sheet = workbook.getSheetAt(1);
            // 获取行
            Iterator<Row> rows = sheet.rowIterator();
            Row row;
            Cell cell;
            while (rows.hasNext()) {
                //获取 行
                row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    // 获取单元格
                    cell = cells.next();
                    HashMap<String, Object> map = new HashMap<>();
                    org.apache.poi.ss.util.CellUtil.setCellStyleProperties(cell,map);
                    Object cellValue = CellUtil.getCellValue(cell);
                    CellStyle cellStyle = cell.getCellStyle();
                    short fillBackgroundColor = cellStyle.getFillBackgroundColor();
                    short fillForegroundColor = cellStyle.getFillForegroundColor();


                    System.out.print(cellValue + " ");
                }
                System.out.println();
            }
            System.out.println();
            //重新写入
            XSSFWorkbook workbook1 = new XSSFWorkbook();//这里也可以设置sheet的Name
            //创建工作表对象
            XSSFSheet sheet1 = workbook1.createSheet();
            //创建工作表的行
            XSSFRow row1 = sheet1.createRow(0);//设置第一行，从零开始
            XSSFCell cell1 = row1.createCell(2);
            cell1.setCellValue("aaaaaaaaaaaa");//第一行第三列为aaaaaaaaaaaa
            CellStyle cellStyle = workbook1.createCellStyle();
            cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
//            cellStyle.setFillForegroundColor(IndexedColors.BLACK1.getIndex());
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


           /* CellStyle style = workbook1.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(FillPatternType.BIG_SPOTS);*/

            cell1.setCellStyle(cellStyle);
            row1.createCell(0).setCellValue(new Date());//第一行第一列为日期
            workbook1.setSheetName(0, "sheet的Name");//设置sheet的Name
            FileOutputStream out = new FileOutputStream("D:\\template\\A1001主协议1.xlsx");
            workbook1.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }


    private static List<Map<String, String>> getParagraph(XWPFParagraph para) {
        //段落格式
        //第一段即首行 为标题
        // XWPFParagraph para = paras.get(0);
        //标题内容
        List<XWPFRun> runsLists = para.getRuns();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> titile = new HashMap<>();
        titile.put("Text", para.getText());//本段全部内容
        titile.put("Alignment", para.getAlignment().toString());
        titile.put("SpacingBetween", para.getSpacingBetween() + "");//行距
        titile.put("SpacingBeforeLines", para.getSpacingBeforeLines() + "");//段前
        titile.put("SpacingAfterLines", para.getSpacingAfterLines() + "");//段后
        titile.put("NumLevelText", para.getNumLevelText() + "");//自动编号格式
        list.add(titile);

        //缩进方式计算
        //先判断缩进方式再进行数值计算
        double ind = -1, ind_left = -1, ind_right = -1, ind_hang = -1;
        String ind_type = "";
        if (para.getIndentationHanging() != -1) {//悬挂缩进
            ind_type = "hang";
            if (para.getIndentationHanging() % 567 == 0) {//悬挂单位为厘米
                ind = para.getIndentationHanging() / 567.0;
                ind_left = (para.getIndentationLeft() - 567.0 * ind) / 210;
            } else {//悬挂单位为字符
                ind = para.getIndentationHanging() / 240;
                ind_left = (para.getIndentationLeft() - para.getIndentationHanging()) / 210;
            }
            ind_right = para.getIndentationRight() / 210.0;
        } else {//首行缩进或者无
            ind_type = "first";
            if (para.getFirstLineIndent() == -1) {
                ind_type = "none";
                ind = 0;
            } else {
                ind = para.getFirstLineIndent() % 567.0 == 0 ? para.getFirstLineIndent() / 567.0 : para.getFirstLineIndent() / 240.0;
            }
            ind_left = para.getIndentationLeft() / 210;
            ind_right = para.getIndentationRight() / 210.0;
        }
        //System.out.println(ind_type+","+ind+","+ind_left+","+ind_right);


        for (XWPFRun run : runsLists) {
            //获取图片
            List<XWPFPicture> pictures = run.getEmbeddedPictures();
            if (pictures.size() > 0) {
                XWPFPicture picture = pictures.get(0);

                XWPFPictureData pictureData = picture.getPictureData();
                //System.out.println(pictureData.getPictureType());
                // System.out.println(picture);
                //实现不了查询图片环绕方式
                //System.out.println(Base64.encode(pictureData.getData()));
            }

            //文字属性
            Map<String, String> titile_map = new HashMap<>();
            titile_map.put("content", run.getText(0));
            String Bold = Boolean.toString(run.isBold());//加粗
            titile_map.put("Bold", Bold);
            String color = run.getColor();//字体颜色
            titile_map.put("Color", color);

            String FontFamily = run.getFontFamily(XWPFRun.FontCharRange.hAnsi);//字体
            titile_map.put("FontFamily", FontFamily);

            String FontName = run.getFontName();//字体
            titile_map.put("FontName", FontName);

            String FontSize = run.getFontSize() + "";//字体大小
            titile_map.put("FontSize", FontSize);

            String Underline = run.getUnderline().name();//字下加线
            titile_map.put("Underline", Underline);

            String UnderlineColor = run.getUnderlineColor();//字下加线颜色
            titile_map.put("UnderlineColor", UnderlineColor);

            String Italic = Boolean.toString(run.isItalic());//字体倾斜
            titile_map.put("Italic", Italic);
            list.add(titile_map);
        }
        return list;
    }
}
