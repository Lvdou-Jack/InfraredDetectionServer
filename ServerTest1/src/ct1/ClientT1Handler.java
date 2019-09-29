package ct1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientT1Handler extends ChannelInboundHandlerAdapter{
	ByteBuf mes;
	
	public  ClientT1Handler() {
		//1.获取消息的字节数组
		byte[] req = "Hello server,I am client,How are you?".getBytes();
		//2.
		mes = Unpooled.buffer(req.length);
		mes.writeBytes(req);
	}
	
	//当客户端和服务端的TCP链路建立成功之后,NIO线程会调用此方法
	@Override
	public void channelActive(ChannelHandlerContext ctx){
		System.out.println("ct1--链路建立,向外发送数据");
		
		ctx.writeAndFlush(mes);
	} 
	
	//服务端返回消息时调用此方法 
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
		System.out.println("ct1--channelRead---st1返回消息");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("From st1 :"+body);
	} 
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
		System.out.println("ct1--调用channelReadComplete");
		ctx.close();
	} 

}
