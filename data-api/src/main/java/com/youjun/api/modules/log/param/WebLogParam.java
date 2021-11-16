package com.youjun.api.modules.log.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.youjun.common.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="WebLogParam", description="操作日志请求参数")
public class WebLogParam extends PageParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "URI")
    private String uri;

    @ApiModelProperty(value = "操作用户")
    private String username;

    @ApiModelProperty(value = "请求类型")
    private String method;

    @ApiModelProperty(value = "操作时间 like")
    private List<LocalDateTime> startTime;

    @ApiModelProperty(value = "IP地址")
    private String ip;
}
