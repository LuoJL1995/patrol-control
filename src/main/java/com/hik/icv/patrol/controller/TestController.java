package com.hik.icv.patrol.controller;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description 测试
 * @Author LuoJiaLei
 * @Date 2020/6/5
 * @Time 10:27
 */
@RestController
public class TestController {


    @ApiOperation(value = "导入仿真文件", notes = "导入仿真文件", httpMethod = "POST")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "fileUpload")
    public void fileUpload(@ApiParam(value = "file", required = false) MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\Administrator\\Desktop\\test2\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
