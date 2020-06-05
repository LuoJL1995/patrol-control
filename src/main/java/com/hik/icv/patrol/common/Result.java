package com.hik.icv.patrol.common;

import com.hik.icv.patrol.enumtype.IResultEnum;
import com.hik.icv.patrol.enumtype.ResultEnum;

import java.io.Serializable;

/**
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:04
 */
public class Result<T> implements Serializable {
    /**
     * 返回消息提示
     */
    private String message = ResultEnum.FAIL.getName();
    /**
     * 返回消息编码
     */
    private String code = ResultEnum.FAIL.getValue();
    private boolean success = false;
    /**
     * 具体数据对象
     */
    private T model;
    /**
     * 如果是列表,返回总条数
     */
    private int totalRecord;

    /**
     * 创建一个result
     */
    public Result() {
    }

    /**
     * 创建一个result。
     *
     * @param success 是否成功
     */
    public Result(boolean success) {
        this.success = success;
    }

    public Result(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public Result(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(T model) {
        this.model = model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getModel() {
        return model;
    }

    public Result setModel(T model) {
        this.model = model;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public void setSuccess(boolean success, String resultCode, String resultMsg) {
        this.success = success;
        this.code = resultCode;
        this.message = resultMsg;
    }

    public Result<T> defaultSuccess() {
        this.success = true;
        this.code = ResultEnum.SUCCESS.getValue();
        this.message = ResultEnum.SUCCESS.getName();
        return this;
    }

    public Result<T> defaultSuccess(T t) {
        this.success = true;
        this.code = ResultEnum.SUCCESS.getValue();
        this.message = ResultEnum.SUCCESS.getName();
        this.model = t;
        return this;
    }

    public Result<T> defaultSuccess(T t, int totalRecord) {
        this.success = true;
        this.code = ResultEnum.SUCCESS.getValue();
        this.message = ResultEnum.SUCCESS.getName();
        this.model = t;
        this.totalRecord = totalRecord;
        return this;
    }


    /**
     * 根据枚举, 设置消息提示和编码
     *
     * @param enumeration
     */
    public void set(IResultEnum enumeration) {
        this.message = enumeration.getName();
        this.code = enumeration.getValue();
    }

    /**
     * 错误返回
     *
     * @param enumeration
     * @return
     */
    public Result<T> errorResult(IResultEnum enumeration) {
        this.setSuccess(false);
        this.set(enumeration);
        return this;
    }

    /**
     * 错误返回
     *
     * @param result
     * @param resultEnum
     * @return
     */
    public static Result errorResult(Result result, ResultEnum resultEnum) {
        result.setSuccess(false);
        result.setCode(resultEnum.getValue());
        result.setMessage(resultEnum.getName());
        return result;
    }

    /**
     * 错误返回
     *
     * @param srcResult
     * @param dstResult
     * @return
     */
    public static Result errorResult(Result srcResult, Result dstResult) {
        dstResult.setSuccess(srcResult.isSuccess());
        dstResult.setCode(srcResult.getCode());
        dstResult.setMessage(srcResult.getMessage());
        return dstResult;
    }

    /**
     * 错误返回
     *
     * @param srcResult
     * @return
     */
    public static Result errorResult(Result srcResult) {
        Result result = new Result();
        result.setSuccess(srcResult.isSuccess());
        result.setCode(srcResult.getCode());
        result.setMessage(srcResult.getMessage());
        return result;
    }
}
