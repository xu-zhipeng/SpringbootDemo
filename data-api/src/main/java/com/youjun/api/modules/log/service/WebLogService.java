package com.youjun.api.modules.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youjun.api.modules.log.param.WebLogParam;
import com.youjun.common.domain.WebLog;

public interface WebLogService extends IService<WebLog> {

    /**
     * 获取模板列表分页
     * @param params
     * @return
     */
    IPage<WebLog> getPage(WebLogParam params);
}
