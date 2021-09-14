package com.youjun.api.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youjun.api.modules.job.model.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author kirk
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLog> {
	
}
