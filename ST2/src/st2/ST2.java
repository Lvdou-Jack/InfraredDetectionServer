package st2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ST2 {

	public void bind(int port) throws Exception {
		// 配置NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 服务器辅助启动类配
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
//					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChildChannelHandler())//
					.option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区 // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// 绑定端口 同步等待绑定成功
			ChannelFuture f = b.bind(port).sync(); // (7)
			// 等到服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅释放线程资源
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	/**
	 * 网络事件处理器
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			int frameLength = 1543;
			// 1.添加对象解码器 负责对序列化POJO对象进行解码 
			//设置对象序列化最大长度为1M 防止内存溢出
			// 2.设置线程安全的WeakReferenceMap对类加载器进行缓存 
			//支持多线程并发访问 防止内存溢出
//			ch.pipeline().addLast(
//					new ObjectDecoder(1024 * 1024, ClassResolvers
//							.weakCachingConcurrentResolver(this.getClass()
//									.getClassLoader())));
			// 添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
			ch.pipeline().addLast(new ObjectEncoder());
			ch.pipeline().addLast(new FixedLengthFrameDecoder(frameLength));
			// 处理网络IO
			ch.pipeline().addLast(new ST2Handler());

		}

	}

 

	public static void main(String[] args) throws Exception {
		new ST2().bind(1234);
	}

}

