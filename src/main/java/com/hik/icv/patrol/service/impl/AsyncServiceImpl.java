package com.hik.icv.patrol.service.impl;

import com.hik.icv.patrol.service.AsyncService;
import com.hik.icv.patrol.utils.SerialPortUtil;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.TooManyListenersException;

import static com.hik.icv.patrol.common.Constant.SERIALPORT_BAUDRATE;
import static com.hik.icv.patrol.common.Constant.SERIALPORT_NAME;

@Service
public class AsyncServiceImpl implements AsyncService {

    private final static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

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

    @Override
    @Async("asyncServiceExecutor")
    public void serialPortAction() {
        try {
            final SerialPort serialPort = SerialPortUtil.openSerialPort(SERIALPORT_NAME, SERIALPORT_BAUDRATE);
            //启动一个线程每2s向串口发送数据，发送1000次hello
            new Thread(() -> {
                int i = 1;
                while (i < 1000) {
                    String s = "hello";
                    byte[] bytes = s.getBytes();
                    SerialPortUtil.sendData(serialPort, bytes);//发送数据
                    i++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //设置串口的listener
            SerialPortUtil.setListenerToSerialPort(serialPort, event -> {
                //数据通知
                if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    byte[] bytes = SerialPortUtil.readData(serialPort);
                    System.out.println("收到的数据长度：" + bytes.length);
                    System.out.println("收到的数据：" + new String(bytes));
                }
            });
            try {
                // sleep 一段时间保证线程可以执行完
                Thread.sleep(3 * 30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | TooManyListenersException e) {
            e.printStackTrace();
        }
    }


}