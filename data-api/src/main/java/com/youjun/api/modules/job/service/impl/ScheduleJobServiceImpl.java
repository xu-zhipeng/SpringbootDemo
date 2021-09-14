package com.youjun.api.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjun.api.domain.AdminUserDetails;
import com.youjun.api.modules.job.enums.ScheduleStatus;
import com.youjun.api.modules.job.mapper.ScheduleJobMapper;
import com.youjun.api.modules.job.model.ScheduleJob;
import com.youjun.api.modules.job.param.ScheduleJobPageParam;
import com.youjun.api.modules.job.service.ScheduleJobService;
import com.youjun.api.modules.job.utils.ScheduleUtils;
import com.youjun.common.util.CollectionUtils;
import com.youjun.common.util.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = this.list();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public IPage<ScheduleJob> queryPage(ScheduleJobPageParam params) {
        Page<ScheduleJob> page = new Page<>(params.getCurrent(), params.getPageSize());
        return this.baseMapper.selectPage(page, new QueryWrapper<ScheduleJob>().lambda()
                .eq(StringUtils.isNotBlank(params.getBeanName()), ScheduleJob::getBeanName, params.getBeanName())
				.orderByAsc(ScheduleJob::getBeanName)
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(ScheduleJob scheduleJob) {
        scheduleJob.setCreatedDt(LocalDateTime.now());
        scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());

        scheduleJob.setCreatedDt(LocalDateTime.now());
        scheduleJob.setCreatedBy(AdminUserDetails.getCurrentUser().getUserId());
        scheduleJob.setModifiedDt(LocalDateTime.now());
        scheduleJob.setModifiedBy(AdminUserDetails.getCurrentUser().getUserId());
        this.save(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        scheduleJob.setModifiedDt(LocalDateTime.now());
        scheduleJob.setModifiedBy(AdminUserDetails.getCurrentUser().getUserId());
        this.updateById(scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        this.removeByIds(Arrays.asList(jobIds));
    }

    @Override
    public int updateBatch(String[] jobIds, int status) {
        int update = 0;
        if (CollectionUtils.isNotEmpty(jobIds)) {
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setStatus(status);
            scheduleJob.setModifiedDt(LocalDateTime.now());
            scheduleJob.setModifiedBy(AdminUserDetails.getCurrentUser().getUserId());
            update = this.baseMapper.update(scheduleJob, new UpdateWrapper<ScheduleJob>().lambda()
                    .in(ScheduleJob::getId, jobIds)
            );
        }
        return update;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.run(scheduler, this.getById(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }

}
