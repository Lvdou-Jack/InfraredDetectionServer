package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	private final ByteBuf firstMessage;
	
	public TimeClientHandler() {
		System.out.println("client--进入TimeClientHandler构造函数");
		byte[] req = "255".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	
	//当客户端和服务端的TCP链路建立成功之后,NIO线程会调用此方法
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		System.out.println("client--进入channelActive");
		ctx.writeAndFlush(firstMessage);
	} 
	
	//服务端返回消息时调用此方法 
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
		System.out.println("client--channelRead服务端返回消息");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is :"+body);
	} 
	
}
