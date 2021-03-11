package com.youjun.api.modules.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  测试java8时间类转换
 *  方法一：下方字段加注解
 *  方法二：全局配置  com.youjun.api.config.DateConfig
 * </p>
 *
 * @author kirk
 * @since 2021/3/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDate {
    private int id;
    private String dateString;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time1;
    private LocalDateTime time2;
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date time3;
    private Date time4;
}
