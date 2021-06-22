package com.youjun.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Controller层的日志封装类
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(schema = "ums",value = "web_log")
@ApiModel(value="WebLog对象", description="操作日志对象")
public class WebLog {
    /**
     * 操作描述
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private LocalDateTime startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 返回结果
     */
    private String result;

}
