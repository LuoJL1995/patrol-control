package com.hik.icv.patrol.netty;

import com.hik.icv.patrol.utils.ByteUtil;
import com.hik.icv.patrol.utils.HexStringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Description 客户端逻辑处理类
 * @Author LuoJiaLei
 * @Date 2020/6/11
 * @Time 16:36
 */
@Component
@ChannelHandler.Sharable
public class ClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    /**
     * @Description 发送给服务器消息的方法
     * @Author LuoJiaLei
     * @Date 2020/6/11
     * @Time 16:34
     * @param ctx:
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,I am client", CharsetUtil.UTF_8));
    }

    /**
     * @Description 回调方法，接收服务器发送的消息
     * @Author LuoJiaLei
     * @Date 2020/6/11
     * @Time 16:35
     * @param ctx: ctx
     * @param msg: 消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String message = msg.toString(StandardCharsets.UTF_8);
        //判断信息是否16进制
        if (!HexStringUtil.isHex(message)) {
            //16进制转String
            message = HexStringUtil.hexToString(message);
        }
        System.out.println(message);
    }

    /**
     * @Description 在处理过程中引发异常时被调用
     * @Author LuoJiaLei
     * @Date 2020/6/11
     * @Time 16:36
     * @param ctx: ctx
     * @param cause: 错误原因
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error(cause.toString());
        ctx.close();
    }

    public void writeAndFlush(Channel channel, String hexString) {
        byte[] bytes = ByteUtil.hexStringToBytes(hexString);
        ByteBuf buffer = channel.alloc().buffer();
        ByteBuf byteBuf = buffer.writeBytes(bytes);
        channel.writeAndFlush(byteBuf);
    }

}