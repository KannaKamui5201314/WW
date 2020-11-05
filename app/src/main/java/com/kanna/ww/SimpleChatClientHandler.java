package com.kanna.ww;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class SimpleChatClientHandler extends  SimpleChannelInboundHandler<String> {

    private static final String TAG = "SimpleChatClientHandler";
    private Context context;

    SimpleChatClientHandler(Context context) {
        this.context = context;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel channel = ctx.channel();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s){
        Log.d(TAG, s);
        Intent intent = new Intent("android.net.GET_MESSAGE");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("playerName", "others");
        intent.putExtra("message", s);
        context.sendBroadcast(intent);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "exception");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}