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
            //设置串口的listener
            SerialPortUtil.setListenerToSerialPort(serialPort, event -> {
                //数据通知
                if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    byte[] bytes = SerialPortUtil.readData(serialPort);
                    System.out.println("收到的数据长度：" + bytes.length);
                    System.out.println("收到的数据：" + new String(bytes));
                }
            });
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | TooManyListenersException e) {
            e.printStackTrace();
        }
    }


}