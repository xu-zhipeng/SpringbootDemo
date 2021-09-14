package com.youjun.api.modules.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youjun.api.modules.job.model.ScheduleJobLog;
import com.youjun.api.modules.job.param.ScheduleJobLogPageParam;

/**
 * 定时任务日志
 *
 * @author kirk
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLog> {

	IPage<ScheduleJobLog> queryPage(ScheduleJobLogPageParam params);
	
}
