package com.youjun.common.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2020/12/22
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="PageParam", description="分页请求基础VO")
public class PageParam {
    @ApiModelProperty(value = "页面条数", required = true)
    protected Integer pageSize=5;

    @ApiModelProperty(value = "当前页", required = true)
    protected Integer current=1;
}
