package com.youjun.api.modules.file.service.impl;


import com.youjun.api.modules.file.service.FileService;
import com.youjun.api.modules.file.util.IpfsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
@Service("ipfsFileServiceImpl")
public class IpfsFileServiceImpl implements FileService {

    @Value("${upload.file.path:#{null}}")
    private String basePath;

    @Value("${request.file.url:#{null}}")
    private String fileUrl;

    /**
     * 上传文件
     *
     * @param sourceFile
     * @return hash字符串+文件扩展名 例：Qmf1TC2ThF2QPTXSzTUq185sTyLKtMyQKxGmRzzYSjM2Ca.png
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile sourceFile) throws IOException {
        //源文件扩展名
        String fileExtension = sourceFile.getOriginalFilename().split("\\.")[1];
        String hash = IpfsUtils.upload(sourceFile.getInputStream());
        return hash + "." + fileExtension;
    }

    /**
     * 从ipfs上获取文件
     *
     * @param hashAndFileExtension
     * @return
     * @throws IOException
     */
    @Override
    public String getFileUrl(String hashAndFileExtension) throws IOException {
        //创建临时文件夹，根据时间每天一个
        String modules = "temp";
        String dateFolder = LocalDate.now().toString();
        String[] split = hashAndFileExtension.split("\\.");
        //hash值
        String hash = split[0];
        //文件扩展名
        String fileExtension = split[1];
        String targetFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
        String targetFileFullPath = basePath + File.separator + modules + File.separator + dateFolder + File.separator + targetFileName;
        IpfsUtils.download(hash, targetFileFullPath);
        String url = fileUrl + modules + "/" + dateFolder + "/" + targetFileName;
        return url;
    }


    /**
     * 从ipfs上获取文件
     *
     * @param hashAndFileExtension
     * @return  返回字节流
     * @throws IOException
     */
    @Override
    public byte[] download2Byte(String hashAndFileExtension) throws IOException {
        String[] split = hashAndFileExtension.split("\\.");
        //hash值
        String hash = split[0];
        //文件扩展名
        String fileExtension = split[1];

        byte[] data = IpfsUtils.download(hash);
        return data;
    }

    /**
     * 上传图片到服务器本地路径 并返回访问路径
     *
     * @param dirPrefix
     * @param sourceFile
     * @return
     */
    @Override
    public String uploadFile(String dirPrefix, MultipartFile sourceFile) {
        throw new RuntimeException("This method is not implemented");
    }
}
