package com.youjun.api.modules.job.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "RunJobParam", description = "执行任务请求参数")
public class RunJobParam {

    @ApiModelProperty(value = "任务id")
    @NotBlank(message = "任务id不能为空")
    private String jobId;

    @ApiModelProperty(value = "执行参数")
    private String params;
}
