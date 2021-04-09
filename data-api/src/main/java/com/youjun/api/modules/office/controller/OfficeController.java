package com.youjun.api.modules.office.controller;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.gson.Gson;
import com.youjun.api.modules.office.model.BussinessLitigationSourceBasicEntity;
import com.youjun.common.api.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

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
        /*//创建文档
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
        table = doc.createTable(10, 4);
        table.getRow(1).getCell(1).setText("代办编号");*/
        //写入文档
        try (FileOutputStream outputStream = new FileOutputStream("D:\\out.docx")) {
            document.write(outputStream);
        }
        return CommonResult.success(null);
    }

    @ApiOperation("生成word报告")
    @GetMapping("/exportWord2007")
    public CommonResult exportWord2007() throws IOException {
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
        FileInputStream inputStream = new FileInputStream("D:\\template\\信访代办导出模板.docx");
        XWPFDocument document = new XWPFDocument(inputStream);
        /*//创建文档
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
        table = doc.createTable(10, 4);
        table.getRow(1).getCell(1).setText("代办编号");*/
        //写入文档
        try (FileOutputStream outputStream = new FileOutputStream("D:\\out.docx")) {
            document.write(outputStream);
        }
        return CommonResult.success(null);
    }

    @RequestMapping("readWordXML")
    public CommonResult readWordXML() {
        try {
            //加载文档
            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）.doc");
//            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）.xml");
//            Document document = XmlUtil.readXML(inputStream);
//            XWPFDocument doc = new XWPFDocument(inputStream);
//            HWPFDocument doc = new HWPFDocument(inputStream);
//            WordExtractor doc = new WordExtractor(inputStream);
            //读取xml文件
            HSSFWorkbook work = new HSSFWorkbook(inputStream);
            //获取xml的表
//            HSSFSheet sheet = work.getSheet("sheet1");
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.success(null);
    }

    @RequestMapping("readWord2003")
    public CommonResult readWord2003() {
        try {
            //加载文档
            InputStream inputStream = new FileInputStream("D:\\template\\HZSDH20210922243（公开电话）-2003.doc");
            HWPFDocument doc = new HWPFDocument(inputStream);
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
            i=0;
            for (XWPFParagraph paragraph : paragraphs) {
                System.out.println("第"+(i++)+"个段落：" + paragraph.getText());
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
            //Workbook workbook = WorkbookFactory.create(inputStream);
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
