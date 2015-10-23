package com.coobud.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
 

import java.util.Date;

import com.coobud.common.netty.codec.NettyMessage;

public class ServerHandler extends ChannelHandlerAdapter {
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
		
		NettyMessage message = (NettyMessage) msg;
		System.out.println(message.getHeader().toString());
		
        System.out.println("服务器读取到客户端请求...");
        ctx.write(msg);
        System.out.println("服务器做出了响应");
    }
     
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("服务器readComplete 响应完成");
    }
     
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("服务器异常退出"+cause.getMessage());
    }

}
