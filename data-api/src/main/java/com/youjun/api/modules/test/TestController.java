package com.youjun.api.modules.test;

import com.youjun.api.modules.test.model.UserDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.Date;

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

    @ApiOperation(value = "测试localDateTime")
    @RequestMapping("/localDateTime")
    public UserDate localDateTime(){
        UserDate userDate = new UserDate();
        userDate.setId(0);
        userDate.setDateString("2021-03-09 15:18:25");
        userDate.setTime1(LocalDateTime.now());
        userDate.setTime2(LocalDateTime.now());
        userDate.setTime3(new Date());
        userDate.setTime4(new Date());
        return userDate;
    }

    @ApiOperation(value = "测试localDateTime")
    @RequestMapping("/saveTime")
    public UserDate saveTime(@RequestBody UserDate userDate){
        return userDate;
    }
}
