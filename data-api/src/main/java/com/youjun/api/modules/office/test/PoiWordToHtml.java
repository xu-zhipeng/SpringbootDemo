package com.youjun.api.modules.office.test;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
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
 *  word转html
 * </p>
 *
 * @author kirk
 * @since 2021/4/12
 */
public class PoiWordToHtml {
    public static void main(String[] args) throws Throwable {
        final String path = "D:\\template\\";
        final String file = "HZSDH20210922243（公开电话）-2003.doc";
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
        FileUtils.writeStringToFile(new File(path, "HZSDH20210922243（公开电话）-2003.html"), content, "utf-8");
    }
}