package com.youjun.api.modules.office.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youjun.api.modules.office.model.SystemExcelTemplateEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * <p>
 * excel模板表 服务类
 * </p>
 *
 * @author kirk
 * @since 2021-04-02
 */
public interface SystemExcelTemplateService extends IService<SystemExcelTemplateEntity> {
    List<SystemExcelTemplateEntity> findTemplateBytemplateName(String templateName);

    <E> List<E> readExcel(MultipartFile file, String templateName, Class<E> cls) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException;

    <E> void writeExcel(List<E> list, String templateName, Class<E> cls,HttpServletResponse response) throws IllegalAccessException, IOException;

    <E> void writeExcelOriginal(List<E> list, String templateName, Class<E> cls,HttpServletResponse response) throws IllegalAccessException, IOException;
}