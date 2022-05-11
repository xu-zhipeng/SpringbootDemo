package com.youjun.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  websocket 消息DTO
 * </p>
 *
 * @author kirk
 * @since 2021/10/18
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="MessageDTO", description="websocket消息DTO")
public class MessageDTO<T> {
    @ApiModelProperty(value = "消息类型")
    private Integer type;

    @ApiModelProperty(value = "消息数据")
    private T data;
}
