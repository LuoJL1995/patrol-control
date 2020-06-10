package com.hik.icv.patrol.controller;

import com.hik.icv.patrol.service.AsyncService;
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

    @ApiOperation(value = "串口线程", notes = "串口线程", httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "serialPortAction")
    public String executeAsyncPool() {
        System.out.println("start");
        //调用service层的任务
        asyncService.serialPortAction();
        System.out.println("end");
        return "success";
    }




}
