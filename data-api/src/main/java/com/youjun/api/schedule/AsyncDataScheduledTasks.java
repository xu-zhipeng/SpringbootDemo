package com.youjun.api.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableAsync
@Slf4j
public class AsyncDataScheduledTasks {

    static private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Async
    @Scheduled(cron = "*/5 * * * * ?")
    public void dataAsync() {
        try {
            ApiDataDownloadRunnable apiDataDownloadRunnable = new ApiDataDownloadRunnable();
            threadPool.submit(apiDataDownloadRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
