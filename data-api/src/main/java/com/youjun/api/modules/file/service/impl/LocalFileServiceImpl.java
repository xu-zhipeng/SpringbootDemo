package com.youjun.api.modules.file.service.impl;


import com.youjun.api.modules.file.service.FileService;
import com.youjun.common.util.FileUtils;
import com.youjun.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * ipfs文件上传下载服务
 * </p>
 *
 * @author kirk
 * @since 2021/3/4
 */
@Slf4j
@Service("localFileServiceImpl")
public class LocalFileServiceImpl implements FileService {

    @Value("${upload.file.path:#{null}}")
    private String basePath;

    @Value("${request.file.url:#{null}}")
    private String fileUrl;

    /**
     * 上传文件
     *
     * @param sourceFile
     * @return 文件访问地址url
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile sourceFile) throws IOException {
        return uploadFile(null, sourceFile);
    }

    /**
     * 从本地服务器上获取文件
     *
     * @param hashAndFileExtension
     * @return
     * @throws IOException
     */
    @Override
    public String downloadFile(String hashAndFileExtension) throws IOException {
        throw new RuntimeException("This method is not implemented");
    }


    /**
     * 从本地服务器上获取文件
     *
     * @param hashAndFileExtension
     * @return 返回字节流
     * @throws IOException
     */
    @Override
    public byte[] download2Byte(String hashAndFileExtension) throws IOException {
        throw new RuntimeException("This method is not implemented");
    }

    /**
     * 上传图片到服务器本地路径 并返回访问路径
     *
     * @param dirPrefix  文件路径前缀
     * @param sourceFile 上传文件
     * @return
     */
    @Override
    public String uploadFile(String dirPrefix, MultipartFile sourceFile) {
        StringBuilder url = new StringBuilder(fileUrl);
        try {
            if (sourceFile.getSize() > 0) {
                //源文件名
                String sourceFileOriginalFilename = sourceFile.getOriginalFilename();
                //源文件扩展名
                String sourceFileExtension = sourceFileOriginalFilename.split("\\.")[1];
                //UUID随机新文件名
                String targetFileName = UUID.randomUUID().toString().replace("-", "") + "." + sourceFileExtension;
                //目标文件存储 全路径
                StringBuilder targetFilePath = new StringBuilder(basePath);
                if (StringUtils.isNotBlank(dirPrefix)) {
                    url.append("/").append(dirPrefix);
                    targetFilePath.append(File.separator).append(dirPrefix);
                }
                targetFilePath.append(File.separator).append(targetFileName);
                //写入文件
                FileUtils.writeBytes(sourceFile.getInputStream(), targetFilePath.toString());
                url.append("/").append(targetFileName);
            }
            return url.toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传失败，原因：{}", e.getMessage());
        }
        return null;
    }
}
