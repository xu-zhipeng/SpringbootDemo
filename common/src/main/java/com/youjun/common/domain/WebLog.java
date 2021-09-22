package com.youjun.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Controller层的日志封装类
 * Created on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(schema = "ums",value = "web_log")
@ApiModel(value="操作日志对象", description="WebLog")
public class WebLog {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "操作用户")
    private String username;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "消耗时间")
    private Integer spendTime;

    @ApiModelProperty(value = "根路径")
    private String basePath;

    @ApiModelProperty(value = "URI")
    private String uri;

    @ApiModelProperty(value = "URL")
    private String url;

    @ApiModelProperty(value = "请求类型")
    private String method;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "请求参数")
    private String parameter;

    @ApiModelProperty(value = "返回结果")
    private String result;

}
