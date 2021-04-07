package com.youjun.api.modules.office.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kirk
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_enum")
@ApiModel(value="SystemEnumEntity对象", description="")
public class SystemEnumEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "类型")
    private String enumType;

    @ApiModelProperty(value = "下拉框键")
    private Integer enumKey;

    @ApiModelProperty(value = "下拉框值")
    private String enumValue;

    @ApiModelProperty(value = "描述")
    private String enumDescribe;


}
