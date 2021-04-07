package com.youjun.api.modules.office.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/4/1
 */
@Data
public class SelectVo {
    @ApiModelProperty("下拉框键")
    private Integer key;

    @ApiModelProperty("下拉框值")
    private String value;
}
