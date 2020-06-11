package com.hik.icv.patrol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * Inbound处理类，是用来处理客户端发送过来的信息
 * Sharable 所有通道都能使用的handler
 */
@Configuration
@ChannelHandler.Sharable
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 获取客户端的内容类
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将传递过来的内容转换为ByteBuf对象
        ByteBuf buf = (ByteBuf) msg;
        //和文件IO一样，用一个字节数组读数据
        byte[] reg = new byte[buf.readableBytes()];
        buf.readBytes(reg);
        //将读取的数据转换为字符串
        String body = new String(reg, "UTF-8");
        System.out.println( "服务端接收的信息是: " + body);
        //给客户端传递的内容，同样也要转换成ByteBuf对象
        String respMsg = "你好我是服务端,当前时间是:";
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        //调用fireChannelRead方法，通知并将数据传给下一个handler
        ctx.fireChannelRead(respByteBuf);

    }

    /**
     * 刷新后才将数据发出到SocketChannel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 关闭
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}