package com.youjun.api.modules.job.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务
 *
 * @author kirk
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(schema = "submission",value = "schedule_job")
@ApiModel(value="ScheduleJob", description="定时任务表")
public class ScheduleJob implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务调度参数key
	 */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	
	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * spring bean名称
	 */
	@ApiModelProperty(value = "spring bean名称")
	@NotBlank(message="bean名称不能为空")
	private String beanName;
	
	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数")
	private String params;
	
	/**
	 * cron表达式
	 */
	@NotBlank(message="cron表达式不能为空")
	@ApiModelProperty(value = "cron表达式")
	private String cronExpression;

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private Integer status;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建人")
	private String createdBy;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createdDt;

	@ApiModelProperty(value = "修改人")
	private String modifiedBy;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime modifiedDt;

}
