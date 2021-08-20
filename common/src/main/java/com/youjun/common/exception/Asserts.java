package com.youjun.common.exception;


import com.youjun.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 *
 * @author kirk
 * @date 2020/2/27
 */
public class Asserts {

    private Asserts() {
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
