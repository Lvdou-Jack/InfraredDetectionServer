package ct1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientT1 {
	
	public void connect(int port,String host)throws Exception{
		System.out.println("ct1--进入connect");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel arg0) throws Exception {
					System.out.println("ct1--initChannel");
					arg0.pipeline().addLast(new ClientT1Handler());
				}
			});
			System.out.println("ct1--发起异步连接操作");
			//发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();
			System.out.println("ct1--等待客户端链路关闭");
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			//释放资源
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception{
    	int port = 1234;
    	String ip  ="47.103.21.160";
    	System.out.println("ct1--attempt to connect");
    	new ClientT1().connect(port, ip);
    }

}
