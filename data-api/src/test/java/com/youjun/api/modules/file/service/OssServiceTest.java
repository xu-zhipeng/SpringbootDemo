package com.youjun.api.modules.file.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.youjun.common.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2022/9/6
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OssServiceTest {

    @Autowired
    OssService ossService;

    @Test
    void upload() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("D:/a.txt");
        String upload = ossService.upload(inputStream, "test/a.txt");
        //upload https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt
    }

    @Test
    void delete() {
        ossService.delete("https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt");
    }

    @Test
    void list() {
        //首部千万不要加/ 比如 /spring-boot-demo 是错的 查不到任何数据
        List<OSSObjectSummary> list = ossService.list("spring-boot-demo");
        //list：1
    }

    @Test
    void download() {
        InputStream download = ossService.download("https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt");
        FileUtils.writeBytes(download,"D:/b.txt");
    }

    @Test
    void getFileUrl() {
        String fileUrl = ossService.getFileUrl("https://kirk-hz-dev.oss-cn-hangzhou.aliyuncs.com/spring-boot-demo/test/a.txt");
    }
}