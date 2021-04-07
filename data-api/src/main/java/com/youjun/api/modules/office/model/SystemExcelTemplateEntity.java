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
 * excel模板表
 * </p>
 *
 * @author kirk
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_excel_template")
@ApiModel(value="SystemExcelTemplateEntity对象", description="excel模板表")
public class SystemExcelTemplateEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "模板数据开始行索引")
    private Integer templateStartIndex;

    @ApiModelProperty(value = "excel字段标题头")
    private String fieldTitle;

    @ApiModelProperty(value = "excel字段列索引")
    private Integer fieldColumnIndex;

    @ApiModelProperty(value = "实体属性名")
    private String fieldName;

    @ApiModelProperty(value = "是否枚举")
    private Integer enumType;

    @ApiModelProperty(value = "描述")
    private String description;


}
