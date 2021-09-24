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
import com.youjun.api.modules.job.service.ScheduleJobLogService;
import com.youjun.api.modules.job.service.ScheduleJobService;
import com.youjun.api.modules.job.utils.ScheduleJobExcute;
import com.youjun.api.modules.job.utils.ScheduleUtils;
import com.youjun.common.exception.ApiException;
import com.youjun.common.util.CollectionUtils;
import com.youjun.common.util.JsonUtils;
import com.youjun.common.util.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = this.list();
        log.info("项目启动,初始化定时器");
        for (ScheduleJob scheduleJob : scheduleJobList) {
            try {
                CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
                // 如果不存在，则创建
                if (cronTrigger == null) {
                    log.info("创建定时器,trigger_name:TASK_{},BeanName:{}",scheduleJob.getId(),scheduleJob.getBeanName());
                    ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
                } else {
                    log.info("更新定时器,trigger_name:TASK_{},BeanName:{}",scheduleJob.getId(),scheduleJob.getBeanName());
                    ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                }
            } catch (Exception ex) {
                log.error("初始化job异常", ex);
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
        try {
            scheduleJob.setStatus(scheduleJob.getStatus());
            scheduleJob.setCreatedDt(LocalDateTime.now());
            scheduleJob.setCreatedBy(AdminUserDetails.getCurrentUser().getUserId());
            scheduleJob.setModifiedDt(LocalDateTime.now());
            scheduleJob.setModifiedBy(AdminUserDetails.getCurrentUser().getUserId());
            this.save(scheduleJob);

            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        } catch (Exception e) {
            log.error("保存定时任务失败", e);
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJob scheduleJob) {
        try {
            ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

            scheduleJob.setModifiedDt(LocalDateTime.now());
            scheduleJob.setModifiedBy(AdminUserDetails.getCurrentUser().getUserId());
            this.updateById(scheduleJob);
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new ApiException(e.getMessage());
        }
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

    @Override
    public void syncRun(String[] jobIds) {
        HashMap<String, String> errorMap = new HashMap<>();
        for (String jobId : jobIds) {
            ScheduleJob scheduleJob = this.getById(jobId);
            ScheduleJobExcute scheduleJobExcute = new ScheduleJobExcute();
            try {
                scheduleJobExcute.executeInternal(scheduleJob);
            } catch (Exception e) {
                log.error("同步执行失败");
                errorMap.put(scheduleJob.getBeanName(),e.getMessage());
            }
        }
        if(CollectionUtils.isNotEmpty(errorMap)){
            throw new ApiException(JsonUtils.toJson(errorMap));
        }
    }

}
