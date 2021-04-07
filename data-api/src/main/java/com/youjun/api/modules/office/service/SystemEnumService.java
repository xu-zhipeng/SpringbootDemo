package com.youjun.api.modules.office.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youjun.api.modules.office.model.SystemEnumEntity;
import com.youjun.api.modules.office.vo.SelectVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kirk
 * @since 2021-04-01
 */
public interface SystemEnumService extends IService<SystemEnumEntity> {
    List<SelectVo> getEnumListByType(String enumType);

    List<SystemEnumEntity> findByType(String enumType);
}
