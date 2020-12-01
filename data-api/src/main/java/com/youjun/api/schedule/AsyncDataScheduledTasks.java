package com.youjun.api.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableAsync
@Slf4j
public class AsyncDataScheduledTasks {

    public static final int PARTITION = 4;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    static private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Async
    @Scheduled(cron = "*/5 * * * * ?")
    public void carTrackDataSync() {
        try {
            ApiDataDownloadRunnable apiDataDownloadRunnable = new ApiDataDownloadRunnable();
            threadPool.submit(apiDataDownloadRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
