package com.hik.icv.patrol.utils;

import javax.usb.*;
import java.util.List;

public class UsbUtil {
    //下边两个参数为系统中usb设备的VID和PID 需要自行配置
    private static short idVendor = (short) 0xFFFF;
    private static short idProduct = (short) 0x0001;

    public static void main(String[] args) {
        byte[] buff=null;
        try {
            UsbPipe sendUsbPipe = new UsbUtil().useUsb();
            //本人测试时 是使用公司的一个读卡器主要对银行卡和身份证进行读取写等相关操作
            //下边为发送的指令,目前大多usb硬件设备都是接受HEX 16进制指令
            //那么如下指令为:363600028000000280 的16进制指令 注意2位为一组16进制字符
            buff[0] = (byte) 0x36;
            buff[1] = (byte) 0x36;
            buff[2] = (byte) 0x00;
            buff[3] = (byte) 0x02;
            buff[4] = (byte) 0x80;
            buff[5] = (byte) 0x00;
            buff[6] = (byte) 0x00;
            buff[7] = (byte) 0x02;
            buff[8] = (byte) 0x80;
            sendMassge(sendUsbPipe, buff);

        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public UsbPipe useUsb() throws Exception{
        UsbInterface iface = linkDevice();
        if (iface == null) {
            return null;
        }
        UsbEndpoint receivedUsbEndpoint,sendUsbEndpoint;

        sendUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(0);
        if (!sendUsbEndpoint.getUsbEndpointDescriptor().toString().contains("OUT")) {
            receivedUsbEndpoint = sendUsbEndpoint;
            sendUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(1);
        } else {
            receivedUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(1);
        }

        //发送：
        UsbPipe sendUsbPipe =  sendUsbEndpoint.getUsbPipe();
        sendUsbPipe.open();

        //接收
        final UsbPipe receivedUsbPipe =  receivedUsbEndpoint.getUsbPipe();
        receivedUsbPipe.open();

        new Thread(() -> {
            try {
                receivedMassge(receivedUsbPipe);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return sendUsbPipe;
    }

    public UsbInterface linkDevice() throws Exception {

        UsbDevice device;
        device = findDevice(UsbHostManager.getUsbServices()
                .getRootUsbHub());
        if (device == null) {
            System.out.println("设备未找到！");
            return null;
        }
        UsbConfiguration configuration = device.getActiveUsbConfiguration();
        UsbInterface iface = null;
        if (configuration.getUsbInterfaces().size() > 0) {
            //此处需要注意 本人在这个地方的时候是进行了debug来看设备到底在map中的key是多少
            //各位如果在此处获取不到设备请自行进行debug看map中存储的设备key到底是多少
            iface = configuration.getUsbInterface((byte) 0);
        } else {
            return null;
        }
        iface.claim(usbInterface -> true);
        return iface;
    }

    public void receivedMassge(UsbPipe usbPipe) throws Exception {
        StringBuilder all = new StringBuilder();
        byte[] b = new byte[64];
        int length;
        while (true) {
            length = usbPipe.syncSubmit(b);//阻塞
            System.out.println("接收长度：" + length);
            for (int i = 0; i < length; i++) {
                //此处会打印所有的返回值 注意返回值全部也都是16进制的
                //比如读取卡号或者身份证号时需要自行转换回10进制
                //并进行补0操作,比如01转换为10进制会变成1需要补0 变成01
                //不然得到的10进制返回值会有问题
                System.out.print(Byte.toUnsignedInt(b[i]) + " ");
                all.append(Byte.toUnsignedInt(b[i])).append(" ");
            }
        }
    }

    public static void sendMassge(UsbPipe usbPipe, byte[] buff) throws Exception {
        //此处为阻塞和非阻塞  非常好理解和多线程一个道理不再解释
        usbPipe.syncSubmit(buff);//阻塞
        //usbPipe.asyncSubmit(buff);//非阻塞
    }

    public UsbDevice findDevice(UsbHub hub) {
        UsbDevice device;
        List list = hub.getAttachedUsbDevices();
        for (int i = 0; i < list.size(); i++) {
            device = (UsbDevice) list.get(i);
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            System.out.println(i + "___" + desc.idVendor() + "___" + desc.idProduct());
            if (desc.idVendor() == idVendor && desc.idProduct() == idProduct) {
                return device;
            }
            if (device.isUsbHub()) {
                device = findDevice((UsbHub) device);
                if (device != null) {
                    return device;
                }
            }
        }
        return null;
    }
}
