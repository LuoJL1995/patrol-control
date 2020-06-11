package com.hik.icv.patrol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * netty 客户端类
 */
@Configuration
public class NettyClient {


    public static void main(String[] args) {
        //客户端只需要创建一个线程就足够了
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端启动类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)//设置线程组
                    .channel(NioSocketChannel.class)//设置通道类型
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 84))//设置IP和端口
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            //阻塞通道
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            group.shutdownGracefully();
        }
    }


}