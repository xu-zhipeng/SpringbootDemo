package com.youjun.api.modules.office.service.impl;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.FormulaCellValue;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjun.api.modules.office.mapper.SystemExcelTemplateMapper;
import com.youjun.api.modules.office.model.SystemEnumEntity;
import com.youjun.api.modules.office.model.SystemExcelTemplateEntity;
import com.youjun.api.modules.office.service.SystemEnumService;
import com.youjun.api.modules.office.service.SystemExcelTemplateService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * <p>
 * excel模板表 服务实现类
 * </p>
 *
 * @author kirk
 * @since 2021-04-02
 */
@Service
public class SystemExcelTemplateServiceImpl extends ServiceImpl<SystemExcelTemplateMapper, SystemExcelTemplateEntity> implements SystemExcelTemplateService {
    @Autowired
    SystemEnumService enumService;

    @Override
    public List<SystemExcelTemplateEntity> findTemplateBytemplateName(String templateName) {
        return this.baseMapper.selectList(
                new QueryWrapper<SystemExcelTemplateEntity>().lambda()
                        .eq(SystemExcelTemplateEntity::getTemplateName, templateName)
                        .orderByAsc(SystemExcelTemplateEntity::getFieldColumnIndex)
        );
    }

    @Override
    public <E> List<E> readExcel(MultipartFile file, String templateName, Class<E> cls) throws IllegalAccessException, InstantiationException, IOException {
        // 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream(), 0);
        //获取模板
        List<SystemExcelTemplateEntity> templateList = this.findTemplateBytemplateName(templateName);
        if (templateList == null || templateList.isEmpty()) {
            log.error("Template is not fund");
            throw new RuntimeException("Template is not fund");
        }
        // 3.读取指定行行到最后一行数据  索引从0开始
        Integer startRowIndex = templateList.get(0).getTemplateStartIndex() - 1;
        List<List<Object>> read = excelReader.read(startRowIndex, excelReader.getRowCount());
        ArrayList<E> list = new ArrayList<>();

        boolean isFirst = true;
        for (List<Object> objects : read) {
            if (isFirst) {
                //首次验证标题
                for (SystemExcelTemplateEntity field : templateList) {
                    Integer columnIndex = field.getFieldColumnIndex() - 1;
                    String title = field.getFieldTitle().trim();
                    String columnName = objects.get(columnIndex).toString().trim();

                    if (StringUtils.hasText(field.getFieldTitle()) && title.equals(columnName)) {

                    } else {
                        //表头不对
                        log.error("Title matching error：{}" + field);
                        throw new RuntimeException("Title matching error：{}" + field);
                    }
                }
                isFirst = false;
                continue;
            }
            E entity = (E) cls.newInstance();
            //获取字段名 array
            Field[] fields = cls.getDeclaredFields();
            for (SystemExcelTemplateEntity templateField : templateList) {
                Integer columnIndex = templateField.getFieldColumnIndex() - 1;
                Object value = objects.get(columnIndex);
                // 判断是否枚举，修改value值
                if (templateField.getEnumType() != null && templateField.getEnumType() == 1) {
                    List<SystemEnumEntity> enumList = enumService.findByType(templateField.getFieldName());
                    boolean isFund = false;
                    for (SystemEnumEntity systemEnum : enumList) {
                        if (value.toString().equalsIgnoreCase(systemEnum.getEnumValue())) {
                            isFund = true;
                            //修改value值
                            value = systemEnum.getEnumKey();
                        }
                    }
                    //未查到枚举新增
                    if (!isFund) {
                        SystemEnumEntity preEnmum = enumList.get(enumList.size() - 1);
                        SystemEnumEntity systemEnumEntity = new SystemEnumEntity();
                        systemEnumEntity.setEnumDescribe(preEnmum.getEnumDescribe());
                        systemEnumEntity.setEnumKey(preEnmum.getEnumKey() + 1);
                        systemEnumEntity.setEnumValue(value.toString());
                        systemEnumEntity.setEnumType(templateField.getFieldName());
                        enumService.save(systemEnumEntity);
                        //修改value值
                        value = systemEnumEntity.getEnumKey();
                    }
                }
                //设置属性值
                String fieldName = templateField.getFieldName().trim();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    //得到属性名
                    String name = field.getName();
                    //比较字段名和属性名是否相同
                    if (name.equals(fieldName)) {
                        //设置值
                        field.setAccessible(true);
                        field.set(entity, value);
                    }
                }
            }
            list.add(entity);
        }
        return list;
    }

    @Override
    public <E> void writeExcel(List<E> list, String templateName, Class<E> cls, HttpServletResponse response) throws IllegalAccessException, IOException {
        //获取模板
        List<SystemExcelTemplateEntity> templateList = this.findTemplateBytemplateName(templateName);
        if (templateList == null || templateList.isEmpty()) {
            log.error("Template is not fund");
            throw new RuntimeException("Template is not fund");
        }
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new RuntimeException("Java reflect error,fields is empty.");
        }
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> row = null;
        //遍历数据
        if (null != list && !list.isEmpty()) {
            for (E entity : list) {
                row = new LinkedHashMap<>();
                for (SystemExcelTemplateEntity templateField : templateList) {
                    //属性值
                    Object value = null;
                    for (Field field : fields) {//遍历
                        //打开私有访问
                        field.setAccessible(true);
                        //获取属性
                        String name = field.getName();
                        if (name.equalsIgnoreCase(templateField.getFieldName())) {
                            //获取属性值
                            value = field.get(entity);
                        }
                    }
                    // 判断是否枚举，修改value值
                    if (value != null && templateField.getEnumType() != null && templateField.getEnumType() == 1) {
                        List<SystemEnumEntity> enumList = enumService.findByType(templateField.getFieldName());
                        boolean isFund = false;
                        for (SystemEnumEntity systemEnum : enumList) {
                            if (value instanceof Integer && (int) value == systemEnum.getEnumKey()) {
                                isFund = true;
                                //修改value值
                                value = systemEnum.getEnumValue();
                            }
                        }
                        //未查到枚举新增
                        if (!isFund) {
                            log.error(templateField.getFieldName() + " enum value not fund");
                            throw new RuntimeException(templateField.getFieldName() + " enum value not fund");
                        }
                    }
                    //填入
                    row.put(templateField.getFieldTitle(), value);
                }
                rows.add(row);
            }
        }

        // 通过工具类创建writer，Excel大数据生成-BigExcelWriter 创建xlsx格式  使用SXSSFWorkbook 生成excel
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        // 合并单元格后的标题行，使用默认标题样式
        String title = templateList.get(0).getDescription();
        writer.merge(templateList.size() - 1, title);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        //out为OutputStream，需要写出到的目标流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        out.close();
    }

    @Override
    public <E> void writeExcelOriginal(List<E> list, String templateName, Class<E> cls, HttpServletResponse response) throws IllegalAccessException, IOException {
        //获取模板
        List<SystemExcelTemplateEntity> templateList = this.findTemplateBytemplateName(templateName);
        if (templateList == null || templateList.isEmpty()) {
            log.error("Template is not fund");
            throw new RuntimeException("Template is not fund");
        }
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new RuntimeException("Java reflect error,fields is empty.");
        }

        //创建 workboot sheet
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        workbook.createSheet("sheet0");
        SXSSFSheet sheet = workbook.getSheetAt(0);
        SXSSFRow row;
        SXSSFCell cell;

        //添加标题  标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        row = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        cell = row.createCell(0);
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, templateList.size() - 1));
        // 设置单元格内容
        String title = templateList.get(0).getDescription();
        cell.setCellValue(title);
        cell.setCellStyle(titleStyle);
        //填入表头
        row = sheet.createRow(1);
        for (SystemExcelTemplateEntity templateField : templateList) {
            row.createCell(templateField.getFieldColumnIndex() - 1).setCellValue(templateField.getFieldTitle());
        }
        //遍历数据
        if (null != list && !list.isEmpty()) {
            int count = 2;
            for (E entity : list) {
                row = sheet.createRow(count);
                for (SystemExcelTemplateEntity templateField : templateList) {
                    //属性值
                    Object value = null;
                    for (Field field : fields) {//遍历
                        //打开私有访问
                        field.setAccessible(true);
                        //获取属性
                        String name = field.getName();
                        if (name.equalsIgnoreCase(templateField.getFieldName())) {
                            //获取属性值
                            value = field.get(entity);
                        }
                    }
                    // 判断是否枚举，修改value值
                    if (value != null && templateField.getEnumType() != null && templateField.getEnumType() == 1) {
                        List<SystemEnumEntity> enumList = enumService.findByType(templateField.getFieldName());
                        boolean isFund = false;
                        for (SystemEnumEntity systemEnum : enumList) {
                            if (value instanceof Integer && (int) value == systemEnum.getEnumKey()) {
                                isFund = true;
                                //修改value值
                                value = systemEnum.getEnumValue();
                            }
                        }
                        //未查到枚举新增
                        if (!isFund) {
                            log.error(templateField.getFieldName() + " enum value not fund");
                            throw new RuntimeException(templateField.getFieldName() + " enum value not fund");
                        }
                    }
                    //填入
                    this.setCellValue(row.createCell(templateField.getFieldColumnIndex() - 1),value,(CellStyle)null);
//                    row.createCell(templateField.getFieldColumnIndex() - 1).setCellValue(value == null ? "" : value.toString());
                }
                count++;
            }
        }
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        //out为OutputStream，需要写出到的目标流
        ServletOutputStream out = response.getOutputStream();
        //写入 流
        workbook.write(out);
        //此处记得关闭输出Servlet流
        out.close();
    }

    private void setCellValue(Cell cell, Object value, CellStyle style) {
        if (null != cell) {
            if (null != style) {
                cell.setCellStyle(style);
            }

            if (null == value) {
                cell.setCellValue("");
            } else if (value instanceof FormulaCellValue) {
                cell.setCellFormula(((FormulaCellValue)value).getValue());
            } else if (value instanceof Date) {
                cell.setCellValue((Date)value);
            } else if (value instanceof TemporalAccessor) {
                if (value instanceof Instant) {
                    cell.setCellValue(Date.from((Instant)value));
                } else if (value instanceof LocalDateTime) {
                    cell.setCellValue((LocalDateTime)value);
                } else if (value instanceof LocalDate) {
                    cell.setCellValue((LocalDate)value);
                }
            } else if (value instanceof Calendar) {
                cell.setCellValue((Calendar)value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean)value);
            } else if (value instanceof RichTextString) {
                cell.setCellValue((RichTextString)value);
            } else if (value instanceof Number) {
                cell.setCellValue(((Number)value).doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }

        }
    }
}
