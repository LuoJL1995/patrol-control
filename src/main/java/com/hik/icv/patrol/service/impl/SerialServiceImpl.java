package com.hik.icv.patrol.service.impl;

import com.hik.icv.patrol.enumtype.ResultEnum;
import com.hik.icv.patrol.netty.NettyClient;
import com.hik.icv.patrol.service.SerialService;
import com.hik.icv.patrol.utils.SerialPortUtil;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TooManyListenersException;

/**
 * @author LuoJialei
 * @description 串口服务实现类
 * @date 2020/6/10
 */
@Service("serialService")
public class SerialServiceImpl implements SerialService {

    @Autowired
    private NettyClient nettyClient;

    private final static Logger logger = LoggerFactory.getLogger(SerialServiceImpl.class);

    private static SerialPort serialPort = null;

    /*static {
        try {
            //默认开启串口COM2，并设置波特率115200超时时间2秒
            serialPort = SerialPortUtil.openSerialPort(SERIALPORT_NAME, SERIALPORT_BAUDRATE);
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException e) {
            logger.error(ResultEnum.SERIAL_OPEN_EXCEPTION.getName() + e);
        }
    }*/

    @Override
    public void serialListen() {
        try {
            //设置串口的listener
            SerialPortUtil.setListenerToSerialPort(serialPort, event -> {
                //数据通知
                if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    byte[] bytes = SerialPortUtil.readData(serialPort);
                    System.out.println("收到的数据长度：" + bytes.length);
                    System.out.println("收到的数据：" + new String(bytes));
                }
            });
        } catch (TooManyListenersException e) {
            logger.error(ResultEnum.SERIAL_LISTEN_EXCEPTION.getName() + e);
        }
    }

    @Override
    public void serialSendData() {
        String s = "hello";
        byte[] bytes = s.getBytes();
        SerialPortUtil.sendData(serialPort, bytes);//发送数据
    }

    @Override
    public void nettySerialSendData(String message) {
        nettyClient.writeAndFlush(message);
    }


}