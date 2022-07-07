package com.youjun.api.modules.file.util;


import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@Slf4j
public class AliyunOssUtils {

    @Value("${oss.endPoint}")
    private String endPoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * 单个文件上传
     * @param fileInputStream 文件流
     * @param filePath 文件名称
     * @return key
     */
    public String uploadFile(InputStream fileInputStream, String filePath){
        log.info("Calling uploadFile with the file path {}", filePath);
        // 创建OSSClient实例
        log.info("Connecting to the OSS server.");
        OSS ossClient = null;
        String result;
        try {
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            log.info("Connect to OSS completed.");
            log.info("Generating file path.");
            filePath = (UUID.randomUUID().toString().replaceAll("/|-", "") + filePath).toLowerCase();
            log.info("The file path is {}", filePath);
            // 创建PutObjectRequest对象
            log.info("Creating putObjectRequest");
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, fileInputStream);
            log.info("Creating putObjectRequest completed.");
            // 上传字符串
            log.info("Uploading the file.");
            ossClient.putObject(putObjectRequest);
            log.info("Uploading completed.");
        } finally {
            // 关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return filePath;
    }


    /**
     * 文件上传
     * @param fileInputStream 文件流
     * @param folder 文件夹名称
     * @param filePath 文件名称
     * @return key
     */
    public String uploadFile(InputStream fileInputStream,String folder,String filePath){
        log.info("Calling uploadFile with the file path {}", filePath);
        // 创建OSSClient实例
        log.info("Connecting to the OSS server.");
        OSS ossClient = null;
        String result;
        try {
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            log.info("Connect to OSS completed.");
            log.info("Generating file path.");
            filePath = (folder + UUID.randomUUID().toString().replaceAll("/|-", "") + filePath).toLowerCase();
            log.info("The file path is {}", filePath);
            // 创建PutObjectRequest对象
            log.info("Creating putObjectRequest");
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, fileInputStream);
            log.info("Creating putObjectRequest completed.");
            // 上传字符串
            log.info("Uploading the file.");
            ossClient.putObject(putObjectRequest);
            log.info("Uploading completed.");
        } finally {
            // 关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return filePath;
    }


    /**
     * 文件上传
     * @param fileInputStream 文件流
     * @param filePath 文件名称
     * @return key
     */
    public String uploadFile(FileInputStream fileInputStream, String filePath, OSS ossClient){
        filePath = UUID.randomUUID().toString().replaceAll("/|-", "") + File.separator + filePath;
        // 创建PutObjectRequest对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, fileInputStream);
        // 上传字符串
        ossClient.putObject(putObjectRequest);
        return filePath;
    }


    /**
     * 批量上传文件
     * @param fileInputStreams
     * @param filePathList
     * @return
     */
    public List<String> uploadFileList(List<FileInputStream> fileInputStreams, List<String> filePathList){
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < fileInputStreams.size(); i++){
            String filePath = this.uploadFile(fileInputStreams.get(i), filePathList.get(i), ossClient);
            res.add(filePath);
        }
        // 关闭OSSClient
        if (ossClient!=null){
            ossClient.shutdown();
        }
        return res;
    }


    /**
     * 获取文件流
     * @param filePath
     * @return
     */
    public InputStream downloadFile(String filePath){
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        boolean exists = ossClient.doesObjectExist(bucketName, filePath);
        if (exists){

            // 获取文件流
            OSSObject ossObject = ossClient.getObject(bucketName, filePath);
            if (ossObject != null && ossObject.getObjectContent() != null){
                ossClient.shutdown();
                return ossObject.getObjectContent();
            }
        }else {
            ossClient.shutdown();
            return null;
        }
        return null;
    }


    /**
     * 下载文件到本地
     * @param filePath
     * @return
     */
    public boolean downloadFile(String filePath, String key){
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        boolean exists = ossClient.doesObjectExist(bucketName, key);
        if (exists){
            File file = new File(filePath);
            // 下载文件到本地
            ossClient.getObject(new GetObjectRequest(bucketName,key), file);
            ossClient.shutdown();
            return true;
        }else {
            ossClient.shutdown();
            return false;
        }
    }


    /**
     * 获取访问链接
     * @param filePath
     * @return
     */
    public String getFileUrl(String filePath){
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        boolean exists = ossClient.doesObjectExist(bucketName, filePath);
        String url = null;
        if (exists){
            // 设置过期时间
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filePath);
            url = ossClient.generatePresignedUrl(bucketName, filePath, expiration).toString();
        }
        ossClient.shutdown();
        return url;
    }


    /**
     * 获取访问链接
     * @param filePath
     * @return
     */
    public String getFileUrl(String filePath,OSS ossClient){
        // 创建OSSClient实例
        boolean exists = ossClient.doesObjectExist(bucketName, filePath);
        String url = null;
        if (exists){
            // 设置过期时间
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filePath);
            url = ossClient.generatePresignedUrl(bucketName, filePath, expiration).toString();
        }
        return url;
    }

    /**
     * 获取访问链接列表
     * @param filePathList
     * @return
     */
    public List<String> getFileUrlList(List<String> filePathList){
        List<String> urlList = new ArrayList<>();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        if (filePathList != null && filePathList.size() > 0 ){
            for (String str : filePathList){
                urlList.add(getFileUrl(str, ossClient));
            }
        }
        if (ossClient != null){
            ossClient.shutdown();
        }
        return urlList;
    }

    /**
     * 获取访问链接列表
     * @param filePathList
     * @return
     */
    public Map<String, String> getFileUrlMap(List<String> filePathList){
        Map<String,String> urlList = new HashMap<>();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        if (filePathList != null && filePathList.size() > 0 ){
            for (String str : filePathList){
                urlList.put(str,getFileUrl(str, ossClient));
            }
        }
        if (ossClient != null){
            ossClient.shutdown();
        }
        return urlList;
    }


    /**
     * 获取图片缩放图列表
     * @param filePathList
     * @return
     */
    public List<String> getPhotoUrlList(List<String> filePathList){
        List<String> urlList = new ArrayList<>();
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        if (filePathList != null && filePathList.size() > 0 ){
            for (String str : filePathList){
                urlList.add(getPhotoUrl(str, ossClient));
            }
        }
        if (ossClient != null){
            ossClient.shutdown();
        }
        return urlList;
    }


    /**
     * 获取处理后到照片
     * @param key
     * @return
     */
    public String getPhotoUrl(String key, OSS ossClient){
        boolean exists = ossClient.doesObjectExist(bucketName, key);
        String url = null;
        if (exists) {
            // 设置图片处理样式。
            String style = "image/resize,w_100,h_100";
            // 设置过期时间 十分钟
            Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            req.setProcess(style);
            URL signUrl = ossClient.generatePresignedUrl(req);
            url = signUrl.toString();
        }

        return url;
    }



    /**
     * 获取处理后到照片
     * @param key
     * @return
     */
    public String getPhotoUrl(String key){
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        boolean exists = ossClient.doesObjectExist(bucketName, key);
        String url = null;
        if (exists) {
            // 设置图片处理样式。
            String style = "image/resize,w_100,h_100";
            // 设置过期时间 十分钟
            Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            req.setProcess(style);
            URL signUrl = ossClient.generatePresignedUrl(req);
            url = signUrl.toString();
        }

        if (ossClient != null){
            ossClient.shutdown();
        }
        return url;
    }



}
