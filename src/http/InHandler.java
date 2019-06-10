package http;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

public class InHandler extends SimpleChannelInboundHandler<HttpObject> {

	public static List<String> list = new Vector<String>();
	public static List<ByteBuf> bufs = new Vector<ByteBuf>();
	public static List<HttpObject> msgs = new Vector<HttpObject>();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		msgs.add(msg);
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest httpRequest = (FullHttpRequest) msg;
			// method
			HttpMethod method = httpRequest.method();
			// decoderMap
			QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
			Map<String, List<String>> decoderMap = decoder.parameters();
			// bodyStr
			if (HttpMethod.POST == method) {
				ByteBuf bodyBuf = httpRequest.content();
				bufs.add(bodyBuf);
				String bodyStr = bodyBuf.toString(CharsetUtil.UTF_8);
				list.add(bodyStr);
				//System.out.println(bodyStr);
			}
			// httpHeaders
			HttpHeaders httpHeaders = httpRequest.headers();

			//System.out.println(method.toString());
			System.out.println("InHandler....");

		}
		// 返回的内容
		ByteBuf content = Unpooled.copiedBuffer("return response!!!", CharsetUtil.UTF_8);
		// http的响应
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
		//主动关闭连接
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
