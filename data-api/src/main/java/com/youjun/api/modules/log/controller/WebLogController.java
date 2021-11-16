package com.youjun.api.modules.log.controller;

import com.youjun.api.modules.log.param.WebLogParam;
import com.youjun.api.modules.log.service.WebLogService;
import com.youjun.common.api.CommonPage;
import com.youjun.common.api.CommonResult;
import com.youjun.common.domain.WebLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
@Api(tags = "WebLogController", description = "操作日志")
public class WebLogController {

    final WebLogService webLogService;

    @Autowired
    public WebLogController(WebLogService webLogService) {
        this.webLogService = webLogService;
    }

    @ApiOperation(value = "获取操作日志列表分页")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult<List<WebLog>> page(@RequestBody WebLogParam params) {
        Map<String, Object> map = CommonPage.restPageToMap(webLogService.getPage(params));
        return CommonResult.success(null, map);
    }

}
