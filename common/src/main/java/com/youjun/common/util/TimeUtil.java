package com.youjun.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2020/12/31
 */
public class TimeUtil {
    /**
     * 获取当前时间（全部）   yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当前日期   yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    /**
     * 获取当前时间   HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * LocalDateTime 转为 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 根据时间获取时间戳
     *
     * @param dateTime
     * @return
     */
    public static Long dateTimeToTimestamp(LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }


    /**
     * 根据时间戳获取日期时间
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime timestampToDateTime(Long timestamp) {
        return timestamp == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static void main(String[] args) {
        System.out.println("当前日期时间：" + getCurrentDateTime());
        System.out.println("当前日期：" + getCurrentDate());
        System.out.println("当前时间getCurrentTime()：" + getCurrentTime());
        System.out.println("当前时间LocalTime.now()：" + LocalTime.now());
    }
}
