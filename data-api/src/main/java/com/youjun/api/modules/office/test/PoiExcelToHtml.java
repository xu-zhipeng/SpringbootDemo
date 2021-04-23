package com.youjun.api.modules.office.test;

import com.youjun.common.util.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * <p>
 * excel转html
 * </p>
 *
 * @author kirk
 * @since 2021/4/12
 */
public class PoiExcelToHtml {
    final static String path = "D:\\template\\";
    final static String file = "诉源事件批量导入模板 -2003.xls";

    public static void main(String args[]) throws Exception {
        InputStream input = new FileInputStream(path + file);
        HSSFWorkbook excelBook = null;
        //兼容 excel 2003/2007以上
        Workbook workbook = WorkbookFactory.create(input);
        // Excel类型
        if (workbook instanceof HSSFWorkbook) {
            // 2003
            excelBook = (HSSFWorkbook) workbook;
        } else if (workbook instanceof XSSFWorkbook) {
            // 2007
            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
            //TODO 将07转03
            throw new RuntimeException("read file error");
        } else {
            throw new RuntimeException("read file error");
        }
        ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        excelToHtmlConverter.processWorkbook(excelBook);
        List pics = excelBook.getAllPictures();
        if (CollectionUtils.isNotEmpty(pics)) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(path + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        //从加载了输入文件中的转换器中提取DOM节点
        Document htmlDocument = excelToHtmlConverter.getDocument();
        //从提取的DOM节点中得到内容
        DOMSource domSource = new DOMSource(htmlDocument);
        //字节码输出流
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //输出流的源头
        StreamResult streamResult = new StreamResult(outStream);
        //转化工厂生成序列转化器
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        //设置序列化内容格式
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        FileUtils.writeStringToFile(new File(path, "诉源事件批量导入模板 -2003.html"), content, "utf-8");
    }
}
