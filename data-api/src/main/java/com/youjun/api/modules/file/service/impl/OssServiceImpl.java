package com.youjun.api.modules.file.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.youjun.api.modules.file.dto.OssCallbackParam;
import com.youjun.api.modules.file.dto.OssCallbackResult;
import com.youjun.api.modules.file.dto.OssPolicyResult;
import com.youjun.api.modules.file.service.OssService;
import com.youjun.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * oss上传管理Service实现类
 * Created by macro on 2018/5/17.
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    @Autowired
    private OSSClient ossClient;

    /**
     * 签名生成
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        OssCallbackParam callback = new OssCallbackParam();
        callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "https://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT);
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(callback).toString().getBytes("utf-8"));
            // 返回结果
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setCallback(callbackData);
            result.setHost(action);
        } catch (Exception e) {
            LOGGER.error("签名生成失败", e);
        }
        return result;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = this.convertToFileUrl(filename);
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }

    @Override
    public String upload(InputStream inputStream, String filePath) {
        filePath = ALIYUN_OSS_DIR_PREFIX.concat(filePath);
        String fileUrl = this.convertToFileUrl(filePath);
        //创建PutObjectRequest对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(ALIYUN_OSS_BUCKET_NAME, filePath, inputStream);
        // 上传字符串
        ossClient.putObject(putObjectRequest);
        return fileUrl;
    }

    @Override
    public String accessUrl(String fileUrl) {
        String filePath = this.convertToFilePath(fileUrl);
        boolean exists = ossClient.doesObjectExist(ALIYUN_OSS_BUCKET_NAME, filePath);
        if (exists) {
            // 设置过期时间
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ALIYUN_OSS_BUCKET_NAME, filePath);
            request.setExpiration(expiration);
            URL url = ossClient.generatePresignedUrl(request);
            if ("http".equals(url.getProtocol())) {
                return url.toString().replaceFirst("http", "https");
            }
            return url.toString();
        }
        throw new RuntimeException("not found file:" + fileUrl);
    }

    @Override
    public InputStream download(String fileUrl) {
        String filePath = this.convertToFilePath(fileUrl);
        boolean exists = ossClient.doesObjectExist(ALIYUN_OSS_BUCKET_NAME, filePath);
        if (exists) {
            // 获取文件流
            OSSObject ossObject = ossClient.getObject(ALIYUN_OSS_BUCKET_NAME, filePath);
            if (ossObject != null && ossObject.getObjectContent() != null) {
                return ossObject.getObjectContent();
            }
        }
        throw new RuntimeException("not found file:" + fileUrl);
    }

    @Override
    public void delete(String fileUrl) {
        String filePath = this.convertToFilePath(fileUrl);
        boolean exists = ossClient.doesObjectExist(ALIYUN_OSS_BUCKET_NAME, filePath);
        if (exists) {
            // 获取文件流
            ossClient.deleteObject(ALIYUN_OSS_BUCKET_NAME, filePath);
        }
    }

    @Override
    public List<OSSObjectSummary> list(String dirPrefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(ALIYUN_OSS_BUCKET_NAME);
        if (StringUtils.isNotBlank(dirPrefix)) {
            listObjectsRequest.setPrefix(dirPrefix);
        }
        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
        return objectListing.getObjectSummaries();
    }

    private String convertToFilePath(String fileUrl) {
        String urlPrefix = "https://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/");
        return fileUrl.substring(urlPrefix.length());
    }

    private String convertToFileUrl(String filePath) {
        String urlPrefix = "https://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/");
        return urlPrefix.concat(filePath);
    }
}
