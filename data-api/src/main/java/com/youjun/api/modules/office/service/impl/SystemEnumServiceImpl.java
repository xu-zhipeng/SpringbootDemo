package com.youjun.api.modules.office.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjun.api.modules.office.mapper.SystemEnumMapper;
import com.youjun.api.modules.office.model.SystemEnumEntity;
import com.youjun.api.modules.office.service.SystemEnumService;
import com.youjun.api.modules.office.vo.SelectVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kirk
 * @since 2021-04-01
 */
@Service
public class SystemEnumServiceImpl extends ServiceImpl<SystemEnumMapper, SystemEnumEntity> implements SystemEnumService {

    @Override
    public List<SelectVo> getEnumListByType(String enumType) {
        return this.baseMapper.selectList(
                new QueryWrapper<SystemEnumEntity>().lambda()
                        .eq(SystemEnumEntity::getEnumType, enumType)
        ).stream().map(systemEnumEntity -> {
            SelectVo selectVo = new SelectVo();
            selectVo.setKey(systemEnumEntity.getEnumKey());
            selectVo.setValue(systemEnumEntity.getEnumValue());
            return selectVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SystemEnumEntity> findByType(String enumType) {
        return this.baseMapper.selectList(
                new QueryWrapper<SystemEnumEntity>().lambda()
                        .eq(SystemEnumEntity::getEnumType, enumType)
                        .orderByAsc(SystemEnumEntity::getEnumKey)
        );
    }
}
