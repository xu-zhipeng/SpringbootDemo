package com.youjun.common.api;

import java.io.Serializable;

/**
 * 封装API的错误码
 * Created on 2019/4/19.
 */
public interface IErrorCode extends Serializable {
    String getCode();

    String getMessage();
}
