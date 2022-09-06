package com.youjun.api.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 文件上传服务
 * </p>
 *
 * @author kirk
 * @since 2021/3/4
 */

public interface FileService {

    /**
     * 上传文件
     * @param sourceFile
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile sourceFile) throws IOException;

    /**
     * 获取文件
     *
     * @param hashAndFileExtension
     * @return
     * @throws IOException
     */
    public String downloadFile(String hashAndFileExtension) throws IOException;

    /**
     * 获取文件字节流
     *
     * @param hashAndFileExtension
     * @return  返回字节流
     * @throws IOException
     */
    public byte[] download2Byte(String hashAndFileExtension) throws IOException;

    /**
     * 上传图片 指定路径前缀 并返回访问路径
     *
     * @param dirPrefix 文件路径前缀
     * @param sourceFile 上传文件
     * @return
     */
    public String uploadFile(String dirPrefix, MultipartFile sourceFile);


}
