package com.youjun.api.modules.file.controller;

import com.youjun.api.modules.file.service.FileService;
import com.youjun.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/3/4
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    @Qualifier("ipfsFileServiceImpl")
    FileService fileService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        try {
            String hashAndFileExtension = fileService.uploadFile(file);
            return CommonResult.success((Object) hashAndFileExtension);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败。");
        }
        return CommonResult.failed();
    }


    /**
     * 上传文件到本地路径，并返回地址
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadLocal")
    public CommonResult uploadLocal(@RequestParam("file") MultipartFile file) {
        String url = fileService.uploadLocal(null, file);
        if (StringUtils.hasText(url)) {
            return CommonResult.success((Object) url);
        }
        return CommonResult.failed();
    }

    @RequestMapping("/download")
    public CommonResult download(@RequestParam("hash") String hashAndFileExtension) {
        try {
            String url = fileService.downloadFile(hashAndFileExtension);
            return CommonResult.success((Object) url);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("下载文件失败。");
        }
        return CommonResult.failed();
    }

    //    @RequestMapping(value = "/getImage", method = RequestMethod.GET, produces = {"application/vnd.ms-excel;charset=UTF-8"})
    @RequestMapping("/getImage")
    public void getImage(@RequestParam("hash") String hashAndFileExtension,HttpServletResponse response) {
        String[] strArr = {"png", "jpeg", "jpg"};
        String[] split = hashAndFileExtension.split("\\.");
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileExtension = split[1];
        if (!StringUtils.hasText(fileExtension) || !Arrays.asList(strArr).contains(fileExtension)) {
            response.setContentType("application/json;charset=UTF-8");
            try {
                OutputStream out = response.getOutputStream();
                out.write("文件格式不正确".getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回头设置
        response.setContentType("image/gif;image/jpeg;image/png;charset=UTF-8");
        byte[] data = null;
        OutputStream out = null;
        try {
            data = fileService.download2Byte(hashAndFileExtension);
            out=response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("下载文件失败。");
        }
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("hash") String hashAndFileExtension) {
        String[] split = hashAndFileExtension.split("\\.");
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileExtension = split[1];
        byte[] data = null;
        try {
            data = fileService.download2Byte(hashAndFileExtension);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("下载文件失败。");
        }
        //Response的Header属性详解 https://blog.csdn.net/weixin_43453386/article/details/83792682
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //声明可下载资源的描述
        headers.setContentDispositionFormData("attachment", fileName + "." + fileExtension);
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
    }

    @RequestMapping("/downloadFile2")
    public void downloadFile(@RequestParam("hash") String hashAndFileExtension, HttpServletRequest request, HttpServletResponse response) {
        String[] split = hashAndFileExtension.split("\\.");
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileExtension = split[1];
        //返回头设置
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + "." + fileExtension);
        byte[] data = null;
        OutputStream out = null;
        try {
            data = fileService.download2Byte(hashAndFileExtension);
            out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("下载文件失败。");
        }
    }


}
