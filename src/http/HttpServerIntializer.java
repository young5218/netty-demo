package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerIntializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		 ChannelPipeline pipeline = ch.pipeline();
		 pipeline.addLast("decompressor", new HttpContentCompressor());
		 pipeline.addLast("serverCodec",new HttpServerCodec());
		 pipeline.addLast("httpObjectAggregator",new HttpObjectAggregator(1024));
		 //处理http请求的handler
	   
	     pipeline.addLast("in1", new InSampleHandler());
	     pipeline.addLast("ou1", new OutHandler());
	     pipeline.addLast("in2", new InSampleHandler());
	     pipeline.addLast("ou2", new OutHandler());
	     pipeline.addLast("in3", new InSampleHandler());
	}

}
