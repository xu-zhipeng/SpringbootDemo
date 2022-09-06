package com.youjun.api.modules.file.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.youjun.api.modules.file.dto.OssCallbackResult;
import com.youjun.api.modules.file.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * oss上传管理Service
 * policy + callback 是前端上传Oss的解决方案，前端调用policy 获取签名，然后自己上传
 */
public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);

    /**
     * oss上传文件
     *
     * @param inputStream 上传文件
     * @param filePath    Oss路径 + 文件名
     * @return 文件访问地址url
     */
    String upload(FileInputStream inputStream, String filePath);

    /**
     * 获取访问Url
     * 有的文件 读权限 为私有的,需要获取临时访问url
     *
     * @param fileUrl
     * @return 返回访问url
     * 例如：
     *   公共读：https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt
     *   临时访问url：https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt?Expires=1662453669&OSSAccessKeyId=TMP.3KgxPThycqhdbAQCUaaoGCPJBuvHXAEZTtzbUYdr73rxy25H3JHGcKzg7fuqbAh4xRpLuk3qjg85HgA8rLyavXU3XgJhFw&Signature=xi4T3vdZDKGX4WalMDo3kt8SAk8%3D
     */
    String getFileUrl(String fileUrl);

    /**
     * oss下载文件
     * @param fileUrl 文件访问地址url
     * @return 下载的文件流
     */
    InputStream download(String fileUrl);


    /**
     * oss删除文件
     * @param fileUrl 文件访问地址url
     * @return 删除结果
     */
    void delete(String fileUrl);

    /**
     * 查看文件列表
     * @param dirPrefix 路径前缀
     * @return
     */
    List<OSSObjectSummary> list(String dirPrefix);
}
