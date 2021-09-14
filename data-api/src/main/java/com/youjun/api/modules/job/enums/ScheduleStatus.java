package com.youjun.api.modules.job.enums;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/9/2
 */
public enum ScheduleStatus {
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 暂停
     */
    PAUSE(0);

    private int value;

    ScheduleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
