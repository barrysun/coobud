package com.coobud.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
 



import java.util.HashMap;
import java.util.logging.Logger;

import com.coobud.common.netty.codec.Header;
import com.coobud.common.netty.codec.NettyMessage;
import com.coobud.common.util.NetWorkUtils;

public class ClientHandler extends ChannelHandlerAdapter {
	
	private static final Logger logger=Logger.getLogger(ClientHandler.class.getName());
    private  ByteBuf firstMessage;
    public ClientHandler(){
        byte[] req ="QUERY TIME ORDER".getBytes();
        firstMessage=Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // ctx.writeAndFlush(firstMessage);
    	ctx.writeAndFlush(buildLoginReq());
        System.out.println("客户端active");
    }
     
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("客户端收到服务器响应数据");
        NettyMessage message=(NettyMessage)msg;
        System.out.println("message:"+message.getHeader());
//        ByteBuf buf=(ByteBuf) msg;
//        byte[] req=new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body=new String(req,"UTF-8");
//        System.out.println("Now is:"+body);
        Thread.sleep(5000);
        ctx.writeAndFlush(buildLoginReq());
       
         
    }
    
    private NettyMessage buildLoginReq() {
    	NettyMessage message = new NettyMessage();
    	Header header = new Header();
    	header.setSessionID(1232);
    	HashMap<String,Object> h=new HashMap<String,Object>();
    	h.put("ip",NetWorkUtils.hostName() );
    	header.setAttachment(h);
    	message.setHeader(header);
    	return message;
        }
     
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("客户端收到服务器响应数据处理完成");
       
    }
     
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warning("Unexpected exception from downstream:"+cause.getMessage());
        ctx.close();
        System.out.println("客户端异常退出");
    }

}
