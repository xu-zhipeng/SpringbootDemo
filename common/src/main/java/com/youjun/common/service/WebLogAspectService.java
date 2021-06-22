package com.youjun.common.service;

import com.youjun.common.domain.WebLog;

public interface WebLogAspectService {
    /**
     * 保存日志
     * @param webLog
     * @return
     */
    Boolean save(WebLog webLog);
}
