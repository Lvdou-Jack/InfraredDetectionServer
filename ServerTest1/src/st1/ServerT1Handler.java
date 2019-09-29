package st1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerT1Handler extends ChannelInboundHandlerAdapter{
	/**
	 * 构造方法*/
	public  ServerT1Handler() {
		System.out.println("st1--进入ServerT1Handler");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		System.out.println("st1--调用channelRead ");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		System.out.println("From client--本次请求的总长度是"+req.length);
		String mes = new String(req,"UTF-8");
		System.out.println("From client--消息内容"+mes);
		
		ByteBuf reply = Unpooled.copiedBuffer("Hello client,I am fine!".getBytes());
		ctx.write(reply);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
		System.out.println("st1--调用channelReadComplete");
		ctx.flush();
	} 

}
