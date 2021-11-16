package com.youjun.api.modules.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjun.api.modules.log.mapper.WebLogMapper;
import com.youjun.api.modules.log.param.WebLogParam;
import com.youjun.api.modules.log.service.WebLogService;
import com.youjun.common.domain.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WebLogServiceImpl extends ServiceImpl<WebLogMapper, WebLog> implements WebLogService {
    @Autowired
    private WebLogMapper webLogMapper;

    @Override
    public IPage<WebLog> getPage(WebLogParam params) {
        Page<WebLog> page = new Page<>(params.getCurrent(), params.getPageSize());
        Page<WebLog> pageResult = this.webLogMapper.getWebLogList(page,params);
        return pageResult;
    }

}

