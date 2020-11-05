package com.kanna.ww;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleChatClient {

    private static final String TAG = "SimpleChatClient";
    private static EventLoopGroup group;
    private static Channel channel;
    private Context context;
    private static ChannelFuture connect;

    SimpleChatClient(Context context) {
        this.context = context;
    }


    void connect() {
        System.out.println(TAG+ ":"+"channel: " + channel);
        System.out.println(TAG+ ":"+"connect: " + connect);
        if (channel != null && channel.isActive()){
            return;
        }
        System.out.println(TAG+ ":"+"connect()");
        group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new SimpleChatClientInitializer(context));

            connect  = bootstrap.connect("120.77.250.134", 7861).sync();

            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) {
                    System.out.println(TAG+ ":"+"channelFuture: " + channelFuture);
                    if(channelFuture.isSuccess()){
                        channel = channelFuture.channel();
                        System.out.println(TAG+ ":"+"连接成功");
                    }
//                    else{
//                        System.out.println(TAG+ ":"+"每隔2s重连....");
//                        channelFuture
//                        .channel()
//                        .eventLoop()
//                        .schedule(new Runnable() {
//
//                                     @Override
//                                     public void run() {
//
//                                     }
//                                 },2, TimeUnit.SECONDS);
//                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

	void sendData(String s){
        if(channel != null && channel.isActive()){
            channel.writeAndFlush(s + "\n");
        }else {
            System.out.println(TAG+ ":"+"通道已关闭");
        }
	}

	void closeConnect(){
        if(channel != null && channel.isActive()){
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
        }
    }
}
