package com.youjun.api.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youjun.api.modules.job.model.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 *
 * @author kirk
 */
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {

}
