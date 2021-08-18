package com.youjun.common.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页数据封装类
 * @author kirk
 * @since 2019/4/19
 */
public class CommonPage<T> {

    private Integer current;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatis Plus 分页结果转化为通用结果
     */
    public static <T> CommonPage<T> restPage(com.baomidou.mybatisplus.core.metadata.IPage<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        //默认页码从1开始
        result.setCurrent((int) pageResult.getCurrent());
        result.setPageSize((int) pageResult.getSize());
        result.setTotal(pageResult.getTotal());
        result.setTotalPage((int) (pageResult.getTotal() / pageResult.getSize() + 1));
        result.setList(pageResult.getRecords());
        return result;
    }

    /**
     * 将data jpa 分页结果转化为通用结果
     */
    public static <T> CommonPage<T> restPage(org.springframework.data.domain.Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        //默认页码从0开始
        result.setCurrent(pageResult.getNumber() + 1);
        result.setPageSize(pageResult.getSize());
        result.setTotal(pageResult.getTotalElements());
        result.setTotalPage((int) (pageResult.getTotalElements() / pageResult.getSize() + 1));
        result.setList(pageResult.getContent());
        return result;
    }

    /**
     * 将MyBatis Plus 分页结果转化为Map
     */
    public static <T> Map<String,Object> restPageToMap(com.baomidou.mybatisplus.core.metadata.IPage<T> pageResult) {
        Map<String,Object> result = new HashMap<>();
        //默认页码从1开始
        result.put("current",pageResult.getCurrent());
        result.put("pageSize",pageResult.getSize());
        result.put("total",pageResult.getTotal());
        result.put("totalPage",(pageResult.getTotal() / pageResult.getSize() + 1));
        result.put("data",pageResult.getRecords());
        return result;
    }

    /**
     * 将data jpa 分页结果转化为Map
     */
    public static <T> Map<String,Object> restPageToMap(org.springframework.data.domain.Page<T> pageResult) {
        Map<String,Object> result = new HashMap<>();
        //默认页码从0开始
        result.put("current",pageResult.getNumber() + 1);
        result.put("pageSize",pageResult.getSize());
        result.put("total",pageResult.getTotalElements());
        result.put("totalPage",(pageResult.getTotalElements() / pageResult.getSize() + 1));
        result.put("data",pageResult.getContent());
        return result;
    }


    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
