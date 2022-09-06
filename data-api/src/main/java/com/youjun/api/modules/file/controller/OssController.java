package com.youjun.api.modules.file.controller;

import cn.hutool.core.util.IdUtil;
import com.youjun.api.modules.file.dto.OssCallbackResult;
import com.youjun.api.modules.file.dto.OssPolicyResult;
import com.youjun.api.modules.file.service.impl.OssServiceImpl;
import com.youjun.common.api.CommonResult;
import com.youjun.common.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Oss相关操作接口
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Controller
@Api(value = "OssController", tags = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }

    @ApiOperation(value = "oss上传")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> upload(MultipartFile file) {
        String fileExtension = FileUtils.getFileExtension(file.getOriginalFilename());
        String filePath = LocalDate.now().toString().concat("/").concat(IdUtil.simpleUUID()).concat(fileExtension);
        try {
            String fileUrl = ossService.upload(file.getInputStream(), filePath);
            return CommonResult.success(fileUrl);
        } catch (IOException e) {
            log.error("上传失败,{}", e.getMessage());
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "oss获取访问链接")
    @RequestMapping(value = "accessUrl", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> accessUrl(String fileUrl) {
        String url = ossService.accessUrl(fileUrl);
        return CommonResult.success(url);
    }

}
