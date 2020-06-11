package com.hik.icv.patrol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Inbound处理类
 * 给客户端返回一个时间戳
 */
@Configuration
public class ServerInboundGetTimeHandler  extends ChannelInboundHandlerAdapter {


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
        //给客户端传递的内容，同样也要转换成ByteBuf对象
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String respMsg = body+ft.format(dNow);
        System.out.println("服务器当前时间是："+ft.format(dNow));
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        //调用write方法，通知并将数据传给outboundHand
        ctx.write(respByteBuf);

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