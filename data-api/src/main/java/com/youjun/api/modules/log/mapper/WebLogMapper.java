package com.youjun.api.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youjun.api.modules.log.param.WebLogParam;
import com.youjun.common.domain.WebLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebLogMapper extends BaseMapper<WebLog>{

    /**
     * 根据查询条件查询数据信息
     * @param params
     * @return
     */
    Page<WebLog> getWebLogList(IPage page, WebLogParam params);
}
