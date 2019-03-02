package com.rpc.zw.netty.handler;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

import com.rpc.zw.netty.context.ClientInfo;
import com.rpc.zw.netty.dispacher.ServerDispacher;
import com.rpc.zw.netty.helper.ThreadPoolHelper;
import com.rpc.zw.netty.manager.ClientManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static ThreadPoolExecutor threadPool;

	public ServerHandler() {
		super();
		threadPool = ThreadPoolHelper.initGeneralThreadPool(100, 100000, "netty-business-process");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		String clientId = getClientId(channel);
		ByteBuf bf = (ByteBuf) msg;
		byte[] data = new byte[bf.readableBytes()];
		bf.readBytes(data);

		ServerDispacher dispacher = new ServerDispacher();
		dispacher.setClientId(clientId);
		dispacher.setData(data);
		threadPool.execute(dispacher);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String clinetId = channel.id().asShortText();
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setClinetId(clinetId);
		clientInfo.setClientIp(getClientIp(channel));
		clientInfo.setChannel(channel);
		// 校验白名单

		// 保存连接信息
		System.out.println("clinet :" + clinetId + "active");
		ClientManager.addClient(clinetId, clientInfo);
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String clientId = getClientId(channel);
		System.out.println("client :" + clientId + " inactive");
		ClientManager.removeClient(clientId);
		super.channelActive(ctx);
	}

	/**
	 * heart beat
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				String clientId = getClientId(ctx.channel());
				System.out.println("client : " + clientId + " heart beat timeout");
				ClientManager.removeClient(clientId);
				ctx.channel().close();
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	protected String getClientId(Channel channel) {
		return channel.id().asShortText();
	}

	protected String getClientIp(Channel channel) {
		InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
		return remoteAddress.getHostString();
	}
}
