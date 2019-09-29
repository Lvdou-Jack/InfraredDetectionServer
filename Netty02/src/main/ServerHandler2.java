package main;

import java.io.File;
import java.io.FileOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler2 extends ChannelInboundHandlerAdapter{
	//用于对网络事件进行读写操作
	private int counter = 0;
	
	public ServerHandler2() {
		System.out.println("server--进入TimeServerHandler");
	}
	
	
	/**
	 * type       data
	 * 1byte      2333byte
	 * type代表请求类型,0是app请求,1是设备传输数据*/
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception{
		System.out.println("server--channelRead被执行 counter is "+ (++counter));
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];//获取缓冲区可读的字节数创建数组
		buf.readBytes(req);//将缓冲区的字节数组复制到新的byte数组中去
		System.out.println("server--data.length="+req.length);
		
		int temp1,temp2;
		temp1 = req[1];
		if(temp1 < 0) {
			temp1 = -temp1+128;
		}
		System.out.println(temp1);
		temp2 = req[2];
		if(temp2 < 0) {
			temp2 = -temp2+128;
		}
		System.out.println(temp2);
		System.out.println((temp1 <<8)+temp2);
		
		//将byte数组中的数据保存到本地
		//"C:\\server\\test"+counter+".txt"
		FileOutputStream out = new FileOutputStream(new File("C:\\server\\test\\test.jpg"));
		out.write(req,0,req.length);
		out.flush();
		out.close();
		File f_picture = new File("C:\\server\\test\\test.jpg");
		if(f_picture.exists())
			 System.out.println("server--当前数据已经保存到本地");
		else
			 System.out.println("server--当前数据保存失败");
		
		ByteBuf respond = Unpooled.copiedBuffer("hello".getBytes());
		ctx.write(respond);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
		System.out.println("server--channelReadComplete被执行");
		ctx.flush();//将消息发送队列中的消息写入到SocketChannel中发送给对方
	} 
	
//	public void convert(byte[] data) {
//		double temp1,temp2;
//		temp1 = data[1] << 8;
//		temp2 = data[2];
//		System.out.println(temp1+temp2);
//		
//	}
	
}
