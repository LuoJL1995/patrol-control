package com.hik.icv.patrol.domain;

import com.hik.icv.patrol.common.Constant;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 实体类基类
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:05
 */
public class BaseDomain implements Serializable {
    private static final long serialVersionUID = -9101066039636865565L;
    /**
     * id
     */
    private String id;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = Constant.DATETIME_FORMAT)
    private Date createdTime;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = Constant.DATETIME_FORMAT)
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public BaseDomain setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public BaseDomain setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public BaseDomain setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }
}
