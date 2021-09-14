package com.youjun.api.modules.job.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务日志
 *
 * @author kirk
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(schema = "submission",value = "schedule_job_log")
@ApiModel(value="ScheduleJobLog", description="定时任务日志表")
public class ScheduleJobLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	@ApiModelProperty(value = "日志id 主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	
	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	private String jobId;
	
	/**
	 * spring bean名称
	 */
	@ApiModelProperty(value = "spring bean名称")
	private String beanName;
	
	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数")
	private String params;
	
	/**
	 * 任务状态     0：失败    1：成功
	 */
	@ApiModelProperty(value = "任务状态    0：失败    1：成功")
	private Integer status;
	
	/**
	 * 失败信息
	 */
	@ApiModelProperty(value = "失败信息")
	private String error;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	@ApiModelProperty(value = "耗时(单位：毫秒)")
	private Integer times;

	@ApiModelProperty(value = "创建人")
	private String createdBy;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createdDt;

	@ApiModelProperty(value = "修改人")
	private String modifiedBy;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime modifiedDt;
	
}
