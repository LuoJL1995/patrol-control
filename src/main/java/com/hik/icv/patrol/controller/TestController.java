package com.hik.icv.patrol.controller;

import com.hik.icv.patrol.service.AsyncService;
import com.hik.icv.patrol.service.SerialService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 测试
 * @Author LuoJiaLei
 * @Date 2020/6/5
 * @Time 10:27
 */
@RestController
public class TestController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private SerialService serialService;

    @ApiOperation(value = "线程", notes = "线程", httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "executeAsyncPool")
    public String executeAsyncPool() {
        System.out.println("start");
        //调用service层的任务
        asyncService.executeAsync();
        System.out.println("end");
        return "success";
    }

    @ApiOperation(value = "串口发送消息", notes = "串口发送消息", httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "serialSendData")
    public String serialSendData() {
        serialService.serialSendData();
        return "success";
    }


}
