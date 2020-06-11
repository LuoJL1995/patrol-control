package com.hik.icv.patrol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.hik.icv.patrol.common.Constant.SERIALPORT_BAUDRATE;
import static com.hik.icv.patrol.common.Constant.SERIALPORT_NAME;

/**
 * @Description netty 客户端
 * @Author LuoJiaLei
 * @Date 2020/6/11
 * @Time 16:15
 */
@Component
public class NettyClient {

    @Autowired
    ClientHandler clientHandler;

    //workGroup线程组用于处理任务,可设置cpu数*2,这里设置为4
    private EventLoopGroup workGroup = new NioEventLoopGroup(4);
    //创建netty的启动类
    private Bootstrap bootstrap = new Bootstrap();
    //创建一个Rxtx通道
    private RxtxChannel channel;


    /**
     * @Description 通过netty通信方式连接串口
     * @Author LuoJiaLei
     * @Date 2020/6/11
     * @Time 16:10
     */
    @PostConstruct
    public void serialInitial() {
        try {
            //设置线程组
            bootstrap.group(workGroup)
                    //设置通道为非阻塞IO
                    .channel(NioServerSocketChannel.class)
                    //立即发送数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    //TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时
                   .option(ChannelOption.SO_KEEPALIVE, true)
                    //Netty参数，连接超时毫秒数
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<RxtxChannel>() {
                        //设置处理请求的逻辑处理类
                        @Override
                        protected void initChannel(RxtxChannel rc) throws Exception {
                            //ChannelPipeline是handler的任务组，里面有多个handler
                            ChannelPipeline pipeline = rc.pipeline();
                            //watch dog 每30秒钟会发送一条空数据用于检测
                            pipeline.addLast(new IdleStateHandler(30, 0, 0));
                            //配置自定义编码器
                            //pipeline.addLast(new StringEncoder());
                            //pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new SerialMsgEncoder());
                            pipeline.addLast(clientHandler);
                        }
                    });

            channel = new RxtxChannel();
            channel.config().setBaudrate(SERIALPORT_BAUDRATE);
            channel.config().setDatabits(RxtxChannelConfig.Databits.DATABITS_8);
            channel.config().setParitybit(RxtxChannelConfig.Paritybit.EVEN);
            channel.config().setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
            bootstrap.connect(new RxtxDeviceAddress(SERIALPORT_NAME)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description netty优雅停机（jvm关闭的时候回收资源）
     * @Author LuoJiaLei
     * @Date 2020/6/11
     * @Time 16:29
     */
    @PreDestroy
    protected void serialDestory() {
        workGroup.shutdownGracefully();
    }


}