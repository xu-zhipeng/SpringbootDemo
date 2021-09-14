package com.youjun.api.modules.job.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.youjun.common.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="ScheduleJobPageParam", description="定时任务分页请求参数")
public class ScheduleJobPageParam extends PageParam {

    @ApiModelProperty(value = "任务id")
    private String jobId;

    @ApiModelProperty(value = "bean名称")
    private String beanName;
}
