package com.hik.icv.patrol.vo;

import gnu.io.SerialPort;

import java.io.Serializable;

public final class SerialPortParameter implements Serializable {

    private static final long serialVersionUID = 8874815910121927542L;

    /**
     * 串口名称(COM0、COM1、COM2等等)
     */
    private String serialPortName;
    /**
     * 波特率
     * 默认：115200
     */
    private int baudRate;
    /**
     * 数据位 默认8位
     * 可以设置的值：SerialPort.DATABITS_5、SerialPort.DATABITS_6、SerialPort.DATABITS_7、SerialPort.DATABITS_8
     * 默认：SerialPort.DATABITS_8
     */
    private int dataBits;
    /**
     * 停止位
     * 可以设置的值：SerialPort.STOPBITS_1、SerialPort.STOPBITS_2、SerialPort.STOPBITS_1_5
     * 默认：SerialPort.STOPBITS_1
     */
    private int stopBits;
    /**
     * 校验位
     * 可以设置的值：SerialPort.PARITY_NONE、SerialPort.PARITY_ODD、SerialPort.PARITY_EVEN、SerialPort.PARITY_MARK、SerialPort.PARITY_SPACE
     * 默认：SerialPort.PARITY_NONE
     */
    private int parity;

    public SerialPortParameter(String serialPortName) {
        this.serialPortName = serialPortName;
        this.baudRate = 115200;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_NONE;
    }

    public SerialPortParameter(String serialPortName, int baudRate) {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_NONE;
    }

    public String getSerialPortName() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }
}