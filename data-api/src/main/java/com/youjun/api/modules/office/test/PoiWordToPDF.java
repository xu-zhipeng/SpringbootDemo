package com.youjun.api.modules.office.test;

import cn.hutool.core.io.FileUtil;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * <p>
 * word转html
 * </p>
 *
 * @author kirk
 * @since 2021/4/12
 */
public class PoiWordToPDF {
    public static void main(String[] args) throws Throwable {
        final String path = "D:\\template\\";
        //使用 aspose 进行 word 转pdf  效果很好,缺点收费,使用的是网上找的license
        doc2pdf(path + "安信收益互换交易确认书模板(框架合约)-4.311-2003.doc", path + "安信收益互换交易确认书模板(框架合约)-4.311-2003.pdf");
        doc2pdf(path + "安信收益互换交易确认书模板(框架合约)-4.311-2007.docx", path + "安信收益互换交易确认书模板(框架合约)-4.311-2007.pdf");
        String content2003 = word2003ToHtml();
//        String content2007 = word2007ToHtml();
        FileUtil.writeString(content2003, new File(path, "安信收益互换交易确认书模板(框架合约)-4.311-2003.html"), "utf-8");
        //使用 itext 进行 html转pdf  效果不好,不能用
//        HtmlConverter.convertToPdf(content2003, new FileOutputStream(path + "安信收益互换交易确认书模板(框架合约)-4.311-2003.pdf"));
//        word2007ToPDF();
//        FileUtil.writeString(content2007, new File(path, "安信收益互换交易确认书模板(框架合约)-4.311-2007.html"), "utf-8");
//        HtmlConverter.convertToPdf(content2007, new FileOutputStream(path + "安信收益互换交易确认书模板(框架合约)-4.311-2007.pdf"));
    }

    public static String word2003ToHtml() throws IOException, TransformerException, ParserConfigurationException {
        final String path = "D:\\template\\";
        final String file = "安信收益互换交易确认书模板(框架合约)-4.311-2003.doc";
        InputStream input = new FileInputStream(path + file);
        HWPFDocument wordDocument = new HWPFDocument(input);
        //生成针对Dom对象的转化器
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //转化器重写内部方法
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                return suggestedName;
            }
        });
        //转化器开始转化接收到的dom对象
        wordToHtmlConverter.processDocument(wordDocument);
        //保存文档中的图片
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
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
        Document htmlDocument = wordToHtmlConverter.getDocument();
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
        return content;
    }

    //使用 XDocReport进行 docx转html
//    public static String word2007ToHtml() throws IOException {
//        final String path = "D:\\template\\";
//        final String file = "安信收益互换交易确认书模板(框架合约)-4.311-2007.docx";
//        InputStream input = new FileInputStream(path + file);
//        XWPFDocument wordDocument = new XWPFDocument(input);
//        //解析 XHTML配置（IURIResolver设置图片存放目录）
//        XHTMLOptions options = XHTMLOptions.create();
//        options.setExtractor(new FileImageExtractor(new File("picturesPath")));
//        options.URIResolver(new BasicURIResolver("picturesPath"));
//        //将 将 XWPFDocument转换成XHTML
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        XHTMLConverter.getInstance().convert(wordDocument, byteArrayOutputStream, options);
//        byteArrayOutputStream.close();
//        String content = byteArrayOutputStream.toString();
//        return content;
//    }

    //使用 XDocReport进行 docx转pdf
//    public static void word2007ToPDF() throws IOException, XDocReportException {
//        final String path = "D:\\template\\";
//        final String file = "安信收益互换交易确认书模板(框架合约)-4.311-2007.docx";
//        InputStream input = new FileInputStream(path + file);
//        XWPFDocument wordDocument = new XWPFDocument(input);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PdfOptions options = PdfOptions.create();
//        PdfConverter.getInstance().convert(wordDocument, byteArrayOutputStream, options);
//        byteArrayOutputStream.close();
//        com.youjun.common.util.FileUtils.writeBytes(byteArrayOutputStream.toByteArray(), path + "安信收益互换交易确认书模板(框架合约)-4.311-2007.pdf");
//    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            ClassPathResource classPathResource = new ClassPathResource("/lib/aspose/license.xml");
            InputStream is = classPathResource.getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String inPath, String outPath) {
        //验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(outPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(file);
            com.aspose.words.Document doc = new com.aspose.words.Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}