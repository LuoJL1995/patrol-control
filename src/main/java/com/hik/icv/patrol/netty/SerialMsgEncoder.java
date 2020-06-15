package com.hik.icv.patrol.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description netty自定义解码器解决byte消息粘包情况
 * @Author LuoJiaLei
 * @Date 2020/6/11
 * @Time 16:25
 */
public class SerialMsgEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf byteBuf) {
        if (msg.length > 0) {
            byteBuf.writeBytes(msg);
        }
    }

}
