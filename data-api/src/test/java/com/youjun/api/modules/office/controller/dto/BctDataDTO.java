package com.youjun.api.modules.office.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "BctDataDTO", description = "bct数据交互dto")
public class BctDataDTO implements Serializable {
    /**
     * mq交互
     */
    @ApiModelProperty(value = "合约ID")
    private String contractId;

    @ApiModelProperty(value = "合约Code")
    private String contractCode;

    @ApiModelProperty(value = "生命周期Id")
    private String lcmId;

    @ApiModelProperty(value = "生命周期事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件产生日期")
    private String eventDate;

}
