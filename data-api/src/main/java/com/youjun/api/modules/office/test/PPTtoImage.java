package com.youjun.api.modules.office.test;


import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * <p>
 * ppt转html
 * </p>
 *
 * @author kirk
 * @since 2021/4/12
 */
public class PPTtoImage {
    public static void main(String[] args) {
        // 读入PPT文件
        File file = new File("D:/template/test.ppt");
        doPPTtoImage(file);
    }

    public static boolean doPPTtoImage(File file) {
        if (file.getName().endsWith(".ppt")) {
            try {
                FileInputStream is = new FileInputStream(file);
                HSLFSlideShow ppt = new HSLFSlideShow(is);
                is.close();
                Dimension pgsize = ppt.getPageSize();
                List<HSLFSlide> slides = ppt.getSlides();
                for (int i = 0; i < slides.size(); i++) {
                    System.out.print("第" + i + "页。");
                    List<List<HSLFTextParagraph>> placeholders = slides.get(i).getTextParagraphs();
                    for(int j=0;j<placeholders.size();j++){
                        List<HSLFTextParagraph> paragraphs = placeholders.get(j);
                        for(int k=0;k<paragraphs.size();k++){
                            HSLFTextParagraph paragraph = paragraphs.get(k);
                            List<HSLFTextRun> textRuns = paragraph.getTextRuns();
                            for(int l=0;l<textRuns.size();l++){
                                HSLFTextRun textRun = textRuns.get(l);
                                textRun.setFontIndex(1);
                                textRun.setFontFamily("宋体");
                                System.out.println(textRun.getRawText());
                            }
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    graphics.setPaint(Color.BLUE);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    //渲染
                    slides.get(i).draw(graphics);
                    // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                    FileOutputStream out = new FileOutputStream("D:/template/pict_" + (i + 1) + ".jpeg");
                    javax.imageio.ImageIO.write(img, "jpeg", out);
                    out.close();
                }
                System.out.println("success!!");
                return true;
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("Can't find the image!");
            } catch (IOException e) {
            }
            return false;
        }else if(file.getName().endsWith(".pptx")){
            try {
                FileInputStream is = new FileInputStream(file);
                XMLSlideShow ppt = new XMLSlideShow(is);
                is.close();
                Dimension pgsize = ppt.getPageSize();
                List<XSLFSlide> slides = ppt.getSlides();
                for (int i = 0; i < slides.size(); i++) {
                    System.out.print("第" + i + "页。");
                    XSLFTextShape[] placeholders = slides.get(i).getPlaceholders();
                    for(int j=0;j<placeholders.length;j++){
                        XSLFTextShape textShape = placeholders[j];
                        List<XSLFTextParagraph> paragraphs = textShape.getTextParagraphs();
                        for(int k=0;k<paragraphs.size();k++){
                            XSLFTextParagraph paragraph = paragraphs.get(k);
                            List<XSLFTextRun> textRuns = paragraph.getTextRuns();
                            for(int l=0;l<textRuns.size();l++){
                                XSLFTextRun textRun = textRuns.get(l);
                                textRun.setFontSize(1.0);
                                textRun.setFontFamily("宋体");
                            }
                            System.out.println(paragraph.getText());
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    graphics.setPaint(Color.BLUE);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    //渲染
                    slides.get(i).draw(graphics);
                    // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                    FileOutputStream out = new FileOutputStream("D:/template/pict_" + (i + 1) + ".jpeg");
                    javax.imageio.ImageIO.write(img, "jpeg", out);
                    out.close();
                }
                System.out.println("success!!");
                return true;
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("Can't find the image!");
            } catch (IOException e) {
            }
            return false;
        }else {
            System.out.println("The image you specify don't exit!");
            return false;
        }
    }
}