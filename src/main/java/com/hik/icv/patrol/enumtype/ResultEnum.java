package com.hik.icv.patrol.enumtype;

import com.hik.icv.patrol.common.Result;

/**
 * @Description 返回值通用
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:06
 */
public enum ResultEnum implements IResultEnum {
    //成功
    SUCCESS("成功", "200"),
    //失败
    FAIL("失败", "100001"),
    //入参为空
    INPUT_EMPTY("入参为空", "100002"),
    //IO异常
    IO_EXCEPTION("IO异常", "100003"),
    //串口开启异常
    SERIAL_OPEN_EXCEPTION("串口开启异常", "100004"),
    //串口监听异常
    SERIAL_LISTEN_EXCEPTION("串口监听异常", "100005");

    private String name;
    private String value;

    ResultEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getName(String value) {
        for (ResultEnum c : ResultEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.name;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 错误返回
     *
     * @param result
     * @param resultEnum
     * @return
     */
    public static void error(ResultEnum resultEnum, Result result) {
        result.setSuccess(false);
        result.setCode(resultEnum.getValue());
        result.setMessage(resultEnum.getName());
    }


}
