package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
	public static void main(String[] args) {
		// �����һ�㶼�ᶨ������ѭ���¼���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new HttpServerIntializer());
			ChannelFuture channelFuture = serverBootstrap.bind(9090).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			System.err.println("http����ʧ��;" + e.getMessage());
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
