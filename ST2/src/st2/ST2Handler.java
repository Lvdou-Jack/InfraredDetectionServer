package st2;

import bean.TestBean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ST2Handler extends ChannelInboundHandlerAdapter {
	// 用于获取客户端发送的信息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("进入channelRead");
		// 用于获取客户端发来的数据信息
//		TestBean body = (TestBean) msg;
//		System.out.println("Server接受的客户端的信息 :" + body.toString());

		// 会写数据给客户端
//		TestBean response = new TestBean("hello client,i had got your mes");
//		ctx.writeAndFlush(response);
		
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];//获取缓冲区可读的字节数创建数组
		buf.readBytes(req);//将缓冲区的字节数组复制到新的byte数组中去
		System.out.println("--请求的总长度是"+req.length);
		int type = (req[0]&0xff);
		System.out.println("---type:"+type);
		if(type==0) {
			// 回写数据给客户端
			double t1 = 25.0;
			double[] d1 = new double[768];
			for(int i=0; i < 768;i++) {
				d1[i] = 1.0;
			}
			TestBean response = new TestBean(t1,d1) ;
			ctx.writeAndFlush(response);
		}
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// cause.printStackTrace();
		ctx.close();
	}

}

