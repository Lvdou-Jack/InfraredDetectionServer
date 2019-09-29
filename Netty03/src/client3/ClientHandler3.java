package client3;

import edu.whut.ruansong.demo_display3.bean.ImageMesBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler3 extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		// 发送消息
		double[] t1 = {1.0,2.0};
		ImageMesBean request1 = new ImageMesBean(25.0,t1,10,10);

		ctx.writeAndFlush(request1);

	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		ImageMesBean response = (ImageMesBean) msg;
		System.out.println(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
