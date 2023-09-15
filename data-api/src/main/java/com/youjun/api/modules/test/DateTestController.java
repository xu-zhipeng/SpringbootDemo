package com.youjun.api.modules.test;

import com.youjun.api.modules.test.model.UserDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2020/12/1
 */
@Slf4j
@RestController
@Api(tags = "TestController", description = "测试Controller")
@RequestMapping("/datetest")
public class DateTestController {

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
        userDate.setTime5(LocalDate.now());
        userDate.setTime6(LocalTime.now());
        return userDate;
    }

    @ApiOperation(value = "测试localDateTime")
    @RequestMapping("/saveTime")
    public UserDate saveTime(@RequestBody UserDate userDate){
        return userDate;
    }


    @ApiOperation(value = "测试localDateTime")
    @RequestMapping("/saveTime1")
    public UserDate saveTime1(@RequestParam(value = "id")int id,
                             @RequestParam(value = "dateString")String dateString,
                             @RequestParam(value = "time1",required = false)LocalDateTime time1,
                             @RequestParam(value = "time2",required = false)LocalDateTime time2,
                             @RequestParam(value = "time3",required = false)Date time3,
                             @RequestParam(value = "time4",required = false)Date time4,
                             @RequestParam(value = "time5",required = false)LocalDate time5,
                             @RequestParam(value = "time6",required = false)LocalTime time6){

        UserDate userDate = new UserDate();
        userDate.setId(id);
        userDate.setDateString(dateString);
        userDate.setTime1(time1);
        userDate.setTime2(time2);
        userDate.setTime3(time3);
        userDate.setTime4(time4);
        userDate.setTime5(time5);
        userDate.setTime6(time6);
        return userDate;
    }

    @ApiOperation(value = "测试logback")
    @GetMapping("/testLogback")
    public String testLogback(){
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        return "testLogback";
    }
}
