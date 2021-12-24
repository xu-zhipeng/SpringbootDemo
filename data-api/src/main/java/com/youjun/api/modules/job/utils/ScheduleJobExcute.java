package com.youjun.api.modules.job.utils;

import com.youjun.api.modules.job.model.ScheduleJob;
import com.youjun.api.modules.job.model.ScheduleJobLog;
import com.youjun.api.modules.job.service.ScheduleJobLogService;
import com.youjun.common.exception.ApiException;
import com.youjun.common.util.SpringContextUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.youjun.api.constant.CommonContants.SCHEDULE;


/**
 * 定时任务
 *
 * @author kirk
 */
public class ScheduleJobExcute extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        executeInternal(scheduleJob, false);
    }

    /**
     * 执行方法
     *
     * @param scheduleJob
     */
    public void executeInternal(ScheduleJob scheduleJob, boolean throwEx) {
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setParams(scheduleJob.getParams());
        log.setCreatedDt(LocalDateTime.now());
        log.setCreatedBy(SCHEDULE);
        log.setModifiedDt(LocalDateTime.now());
        log.setModifiedBy(SCHEDULE);
        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            this.log.debug("任务准备执行，任务ID{}，任务名称{}", scheduleJob.getId(), scheduleJob.getBeanName());

            Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
            Method method = target.getClass().getMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态     0：失败    1：成功
            log.setStatus(1);
            this.log.debug("任务执行完毕，任务ID：{}，任务名称：{}，总共耗时：{}毫秒", scheduleJob.getId(), scheduleJob.getBeanName(), times);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态    0：失败    1：成功
            log.setStatus(0);
            String message;
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                targetException.printStackTrace(printWriter);
                message = String.format("任务执行失败，任务ID：%s，任务名称：%s，\n内容：%s，\n位置：%s", scheduleJob.getId(), scheduleJob.getBeanName(), targetException.getMessage(), stringWriter.toString());
            } else {
                e.printStackTrace(printWriter);
                message = String.format("任务执行失败，任务ID：%s，任务名称：%s，\n内容：%s，\n位置：%s", scheduleJob.getId(), scheduleJob.getBeanName(), e.getMessage(), stringWriter.toString());
            }
            printWriter.flush();
            stringWriter.flush();
            this.log.error(message);
            if (null != message && message.length() > 5000) {
                message = message.substring(0, 5000);
            }
            log.setError(message);
            if (throwEx) {
                throw new ApiException(message);
            }
        } finally {
            scheduleJobLogService.save(log);
        }
    }
}
