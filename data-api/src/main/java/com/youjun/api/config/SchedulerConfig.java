package com.youjun.api.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Properties;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {
    private final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    /**
     * 覆盖配置
     *
     * @param dataSource
     * @return
     */
    /*@Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);

        //quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "SubmissionScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        //JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //集群配置
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

        //PostgreSQL数据库，需要打开此注释
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");

        factory.setQuartzProperties(prop);

        factory.setSchedulerName("SubmissionScheduler");
        //延时启动
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);

        return factory;
    }*/

    /**
     * 基于Springboot默认配置进行更改配置
     */
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(){
        return (schedulerFactoryBean)->{
            //quartz参数
            Properties prop = new Properties();
            prop.put("org.quartz.scheduler.instanceName", "SubmissionScheduler");
            prop.put("org.quartz.scheduler.instanceId", "AUTO");
            //线程池配置
            prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            prop.put("org.quartz.threadPool.threadCount", "20");
            prop.put("org.quartz.threadPool.threadPriority", "5");
            //JobStore配置
            prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
            //集群配置
            prop.put("org.quartz.jobStore.isClustered", "true");
            prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
            prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

            prop.put("org.quartz.jobStore.misfireThreshold", "12000");
            prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
            prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

            //PostgreSQL数据库，需要打开此注释
            prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");

            schedulerFactoryBean.setQuartzProperties(prop);

            schedulerFactoryBean.setSchedulerName("SubmissionScheduler");
            //延时启动
            schedulerFactoryBean.setStartupDelay(30);
            schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
            //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            //设置自动启动，默认为true
            schedulerFactoryBean.setAutoStartup(true);
        };
    }
}
