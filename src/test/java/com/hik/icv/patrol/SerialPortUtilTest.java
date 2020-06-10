package com.hik.icv.patrol;

import com.hik.icv.patrol.utils.SerialPortUtil;
import gnu.io.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TooManyListenersException;

public class SerialPortUtilTest {

    /**
     * 测试获取串口列表
     */
    @Test
    public void getSystemPortList() {

        //获取可用的串口列表
        List<String> portList = SerialPortUtil.getSerialPortList();
        System.out.println(portList);

        //通过串口名称打开串口
        try {
            SerialPortUtil.openSerialPort("COM2");
        } catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试串口打开，读，写操作
     */
    @Test
    public void serialPortAction() {
        try {
            final SerialPort serialPort = SerialPortUtil.openSerialPort("COM2", 115200);
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
