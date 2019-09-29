package p;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{
	//用于对网络事件进行读写操作
	
	public TimeServerHandler() {
		System.out.println("server--进入TimeServerHandler");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception{
		System.out.println("channelRead被执行");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];//获取缓冲区可读的字节数创建数组
		buf.readBytes(req);//将缓冲区的字节数组复制到新的byte数组中去
		String body = new String (req,"UTF-8");//获取请求消息
		System.out.println("The time server receive order:"+body);
		String currentTime = 
				"QUERY-TIME-ORDER".equalsIgnoreCase(body)?new java.util.Date(
				System.currentTimeMillis()).toString():"BAD ORDER";
				//如果是QUERY TIME ORDER则创建应答消息
	    ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
	    ctx.write(resp);//异步发送应答消息给客户端
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
		System.out.println("channelReadComplete被执行");
		ctx.flush();//将消息发送队列中的消息写入到SocketChannel中发送给对方
		//调用write()方法只是将待发送的消息放到发送缓冲数组中
	} 
	
}