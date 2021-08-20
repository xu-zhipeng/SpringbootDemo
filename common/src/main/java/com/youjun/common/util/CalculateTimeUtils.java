package com.youjun.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>
 * 计算程序运行时间工具类
 * </p>
 *
 * @author kirk
 * @since 2021/6/9
 */
public class CalculateTimeUtils {
    private CalculateTimeUtils() {
    }

    private static final Logger log = LoggerFactory.getLogger(CalculateTimeUtils.class);

    private static volatile Long startTime;
    private static volatile Long endTime;

    public static void start() {
        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
        log.info("开始计时");
    }

    public static void spendTime() {
        spendTime(null);
    }

    public static void spendTime(String message) {
        if (Objects.isNull(endTime)) {
            endTime = System.currentTimeMillis();
        } else if (Objects.nonNull(endTime)) {
            startTime = endTime;
            endTime = System.currentTimeMillis();
        }
        caculateTime(message);
    }

    private static void caculateTime(String message) {
        if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
            Long spendTime = endTime - startTime;
            if (Objects.nonNull(message)) {
                log.info("{}   花了{}毫秒", message, spendTime);
            } else {
                log.info("花了{}毫秒", spendTime);
            }
        }
    }
}
