package com.youjun.api.modules.office.controller;

import cn.hutool.json.JSON;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.google.gson.Gson;
import com.youjun.api.modules.office.model.BussinessLitigationSourceBasicEntity;
import com.youjun.common.api.CommonResult;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/3/31
 */
public class TestSXSSFWorkbook {
    public static void test1(){
        try {
            long t1 = System.currentTimeMillis();
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            workbook.createSheet("aaa");
            SXSSFSheet aaa = workbook.getSheetAt(0);
            for (int i=0;i<1000000;i++){
                aaa.createRow(i);
                aaa.getRow(i).createCell(0).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(1).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(2).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(3).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(4).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
            }
            OutputStream outputStream = null;
            // 打开目的输入流，不存在则会创建
            outputStream = new FileOutputStream("D:\\out.xlsx");
            workbook.write(outputStream);
            outputStream.close();
            long t2 = System.currentTimeMillis();
            System.out.println("SXSSFWorkbook : 100w条数据写入Excel 消耗时间："+ (t2-t1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test2(){
        try {
            long t1 = System.currentTimeMillis();
            XSSFWorkbook workbook = new XSSFWorkbook();
            workbook.createSheet("aaa");
            XSSFSheet aaa = workbook.getSheetAt(0);
            for (int i=0;i<1000000;i++){
                aaa.createRow(i);
                aaa.getRow(i).createCell(0).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(1).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(2).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(3).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
                aaa.getRow(i).createCell(4).setCellValue("aaaaaaaaaaaaaaaaaaaaaaa");
            }
            OutputStream outputStream = null;
            // 打开目的输入流，不存在则会创建
            outputStream = new FileOutputStream("D:\\out2.xlsx");
            workbook.write(outputStream);
            outputStream.close();
            long t2 = System.currentTimeMillis();
            System.out.println("XSSFWorkbook : 100w条数据写入Excel 消耗时间："+ (t2-t1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
