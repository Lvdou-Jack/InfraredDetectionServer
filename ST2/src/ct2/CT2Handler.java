package ct2;

import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

import bean.TestBean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

//用于读取客户端发来的信息
public class CT2Handler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("发送请求");
		byte[] req = new byte[1543];
        for(int i = 0; i< 1543;i++){//用全0初始化数组
            req[i] = (byte)0;
        }
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);

	}

	// 只是读数据，没有写数据的话
	// 需要自己手动的释放的消息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		System.out.println("接收回复");
		TestBean response = (TestBean) msg;
		double t1 = response.gettAverage();
		double[] d1 = response.getTemp();
		System.out.println(t1);
		System.out.println(Arrays.toString(d1));
//		System.out.println(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

}

