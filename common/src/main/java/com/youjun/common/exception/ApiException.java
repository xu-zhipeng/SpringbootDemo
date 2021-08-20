package com.youjun.common.exception;


import com.youjun.common.api.IErrorCode;
import com.youjun.common.api.ResultCode;

/**
 * 自定义API异常
 * Created by macro on 2020/2/27.
 */
public class ApiException extends RuntimeException {
    private final IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        this.errorCode = ResultCode.FAILED;
    }

    public ApiException(String message,Object... args) {
        this(splicingMsgWithArgs(message,args));
    }

    public ApiException(Throwable cause) {
        super(cause);
        this.errorCode = ResultCode.FAILED;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ResultCode.FAILED;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

    public static String splicingMsgWithArgs(String message, Object... args) {
        if (null != args && args.length != 0) {
            for (Object arg : args) {
                message = message.replaceFirst("\\{\\}", arg.toString());
            }
        }
        return message;
    }
}
