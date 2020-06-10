package com.hik.icv.patrol.service.impl;

import com.hik.icv.patrol.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description 线程服务实现类
 * @Author LuoJiaLei
 * @Date 2020/6/10
 * @Time 15:16
 */
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        System.out.println("start executeAsync");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end executeAsync");
    }

}