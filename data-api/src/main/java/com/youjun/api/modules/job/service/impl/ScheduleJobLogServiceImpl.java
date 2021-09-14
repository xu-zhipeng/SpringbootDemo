package com.youjun.api.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjun.api.modules.job.mapper.ScheduleJobLogMapper;
import com.youjun.api.modules.job.model.ScheduleJobLog;
import com.youjun.api.modules.job.param.ScheduleJobLogPageParam;
import com.youjun.api.modules.job.service.ScheduleJobLogService;
import com.youjun.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {

    @Override
    public IPage<ScheduleJobLog> queryPage(ScheduleJobLogPageParam params) {
        Page<ScheduleJobLog> page = new Page<>(params.getCurrent(), params.getPageSize());
        return this.baseMapper.selectPage(page, new QueryWrapper<ScheduleJobLog>().lambda()
                .eq(StringUtils.isNotBlank(params.getJobId()), ScheduleJobLog::getJobId, params.getJobId())
                .eq(StringUtils.isNotBlank(params.getStatus()), ScheduleJobLog::getStatus, params.getStatus())
                .like(StringUtils.isNotBlank(params.getBeanName()), ScheduleJobLog::getBeanName, params.getBeanName())
                .orderByDesc(ScheduleJobLog::getCreatedDt)
        );
    }

}
