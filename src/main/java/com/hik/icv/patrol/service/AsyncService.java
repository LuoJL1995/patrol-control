package com.hik.icv.patrol.service;

/**
 * @Description 线程服务
 * @Author LuoJiaLei
 * @Date 2020/6/10
 * @Time 10:29  
 */
public interface AsyncService {
    
    /**
     * @Description 执行异步任务(测试)
     * @Author LuoJiaLei
     * @Date 2020/6/10
     * @Time 11:13
     */
    void executeAsync();

    /**
     * @Description 串口打开并监听
     * @Author LuoJiaLei
     * @Date 2020/6/10
     * @Time 11:13
     */
    void serialPortAction();
}