package com.youjun.api.modules.job.controller;


import com.youjun.api.modules.job.model.ScheduleJob;
import com.youjun.api.modules.job.param.ScheduleJobPageParam;
import com.youjun.api.modules.job.service.ScheduleJobService;
import com.youjun.common.api.CommonPage;
import com.youjun.common.api.CommonResult;
import com.youjun.common.log.MethodLog;
import com.youjun.common.util.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 定时任务
 *
 * @author kirk
 */
@RestController
@Api(tags = "ScheduleJobController", description = "定时任务管理")
@RequestMapping("/job/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @ApiOperation(value = "定时任务列表")
    @PostMapping("/page")
    public CommonResult<List<ScheduleJob>> page(ScheduleJobPageParam params) {
        Map<String, Object> map = CommonPage.restPageToMap(scheduleJobService.queryPage(params));
        return CommonResult.success(null, map);
    }

    @ApiOperation(value = "定时任务信息")
    @PostMapping("/info")
    public CommonResult info(@RequestBody Long jobId) {
        ScheduleJob schedule = scheduleJobService.getById(jobId);

        return CommonResult.success(schedule);
    }

    @MethodLog()
    @ApiOperation(value = "保存定时任务")
    @PostMapping("/saveOrUpdate")
    public CommonResult saveOrUpdate(@RequestBody @Validated ScheduleJob scheduleJob) {
        if(Objects.nonNull(scheduleJob.getId())){
            scheduleJobService.update(scheduleJob);
        }else {
            scheduleJobService.saveJob(scheduleJob);
        }
        return CommonResult.success(null);
    }

    @MethodLog()
    @ApiOperation(value = "删除定时任务")
    @PostMapping("/delete")
    public CommonResult delete(@RequestBody String[] jobIds) {
        if(CollectionUtils.isEmpty(jobIds)){
            return CommonResult.failed();
        }
        scheduleJobService.deleteBatch(jobIds);

        return CommonResult.success(null);
    }

    @MethodLog()
    @ApiOperation("立即执行任务")
    @PostMapping("/run")
    public CommonResult run(@RequestBody String[] jobIds) {
        if(CollectionUtils.isEmpty(jobIds)){
            return CommonResult.failed();
        }
        scheduleJobService.run(jobIds);

        return CommonResult.success(null);
    }

    @MethodLog()
    @ApiOperation(value = "暂停定时任务")
    @PostMapping("/pause")
    public CommonResult pause(@RequestBody String[] jobIds) {
        if(CollectionUtils.isEmpty(jobIds)){
            return CommonResult.failed();
        }
        scheduleJobService.pause(jobIds);

        return CommonResult.success(null);
    }

    @MethodLog()
    @ApiOperation(value = "恢复定时任务")
    @PostMapping("/resume")
    public CommonResult resume(@RequestBody String[] jobIds) {
        if(CollectionUtils.isEmpty(jobIds)){
            return CommonResult.failed();
        }
        scheduleJobService.resume(jobIds);

        return CommonResult.success(null);
    }

}
