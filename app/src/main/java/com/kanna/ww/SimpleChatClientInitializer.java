package com.kanna.ww;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {

	private Context context;

	SimpleChatClientInitializer(Context context) {
		this.context = context;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("encoder", new StringEncoder(StandardCharsets.UTF_8));
		pipeline.addLast("decoder", new StringDecoder(Charset.forName("GBK")));
		pipeline.addLast("handler", new SimpleChatClientHandler(context));
	}
}
