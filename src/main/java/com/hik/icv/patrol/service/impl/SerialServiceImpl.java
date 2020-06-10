package com.hik.icv.patrol.service.impl;

import com.hik.icv.patrol.enumtype.ResultEnum;
import com.hik.icv.patrol.service.SerialService;
import com.hik.icv.patrol.utils.SerialPortUtil;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TooManyListenersException;

import static com.hik.icv.patrol.common.Constant.SERIALPORT_BAUDRATE;
import static com.hik.icv.patrol.common.Constant.SERIALPORT_NAME;

/**
 * @author LuoJialei
 * @description 串口服务实现类
 * @date 2020/6/10
 */
@Service("serialService")
public class SerialServiceImpl implements SerialService {

    private final static Logger logger = LoggerFactory.getLogger(SerialServiceImpl.class);

    @Override
    @PostConstruct
    public void serialPortAction() {
        try {
            //默认开启串口COM2，并设置波特率115200超时时间2秒
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
            logger.error(ResultEnum.SERIAL_LISTEN_EXCEPTION.getName() + e);
        }
    }
}