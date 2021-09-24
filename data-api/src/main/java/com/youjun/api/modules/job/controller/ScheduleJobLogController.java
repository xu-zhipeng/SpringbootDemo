package com.youjun.api.modules.job.controller;

import com.youjun.api.modules.job.model.ScheduleJobLog;
import com.youjun.api.modules.job.param.ScheduleJobLogPageParam;
import com.youjun.api.modules.job.service.ScheduleJobLogService;
import com.youjun.common.api.CommonPage;
import com.youjun.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author kirk
 */
@RestController
@Api(tags = "ScheduleJobLogController", description = "定时任务日志管理")
@RequestMapping("/job/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	

	@ApiOperation(value = "定时任务日志列表")
	@PostMapping("/page")
	public CommonResult<List<ScheduleJobLog>> page(ScheduleJobLogPageParam params){
		Map<String, Object> map = CommonPage.restPageToMap(scheduleJobLogService.queryPage(params));
		return CommonResult.success(null, map);
	}
	

	@ApiOperation(value = "定时任务日志信息")
	@PostMapping("/info")
	public CommonResult info(@RequestBody Long logId){
		ScheduleJobLog log = scheduleJobLogService.getById(logId);

		return CommonResult.success(log);
	}
}
