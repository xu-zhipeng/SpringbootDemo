package com.youjun.api.modules.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2020/12/1
 */
@RestController
@Api(tags = "TestController", description = "测试Controller")
@RequestMapping("/Test")
public class TestController {

    @ApiOperation(value = "测试method")
    @RequestMapping("/test")
    public String test(){
        return "hell test";
    }
}
