package com.youjun.api.modules.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youjun.api.modules.job.model.ScheduleJob;
import com.youjun.api.modules.job.param.RunJobParam;
import com.youjun.api.modules.job.param.ScheduleJobPageParam;

/**
 * 定时任务
 *
 * @author kirk
 */
public interface ScheduleJobService extends IService<ScheduleJob> {

	IPage<ScheduleJob> queryPage(ScheduleJobPageParam params);

	/**
	 * 保存定时任务
	 */
	void saveJob(ScheduleJob scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJob scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(String[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(String[] jobIds, int status);
	
	/**
	 * 立即执行(异步)
	 */
	void run(RunJobParam runJobParam);
	
	/**
	 * 暂停运行
	 */
	void pause(String[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(String[] jobIds);

	/**
	 * 立即执行(同步)
	 */
	void syncRun(RunJobParam runJobParam);
}
