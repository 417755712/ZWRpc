package com.rpc.zw.netty.context;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;

import com.rpc.zw.netty.handler.ServerHandler;
import com.rpc.zw.netty.manager.ContextManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * �ⲿ�����ĳ�ʼ����
 * 
 * @author ZZ
 *
 */
public class RPCServerContext {
	// ��֧��һ��Server�������(���������˿�)
	private static volatile boolean STARTED = false;
	// ������ʱʱ��(Ĭ��20)
	private int heartTimeOut = 20;
	// ���δ�����������ֽڳ���(Ĭ�� 2M)
	private int maxFrameLength = 2 * 1024 * 1000;

	/**
	 * start server
	 * 
	 * @param ctx
	 * @param port
	 * @throws Exception
	 */
	public void serverStart(ApplicationContext ctx, int port) throws Exception {
		synchronized (RPCServerContext.class) {
			if (STARTED) {
				throw new Exception("server start repeat");
			}
			if (ctx == null) {
				throw new Exception("Spring ApplicationContext is null");
			}
			ContextManager.setSpringCtx(ctx);
			ContextManager.getServiceRoute().init();
			nettyStart(port);
		}
	}

	public RPCServerContext setHeartTimeOut(int second) {
		heartTimeOut = second;
		return this;
	}

	public RPCServerContext setMaxFrameLength(int length) {
		maxFrameLength = length;
		return this;
	}

	/**
	 * netty start
	 * 
	 * @param port
	 * 
	 * @throws Exception
	 */
	private void nettyStart(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBoot = new ServerBootstrap();
			serverBoot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new IdleStateHandler(heartTimeOut, 0, 0, TimeUnit.SECONDS));
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, 0, 4, 0, 0));
							// ch.pipeline().addLast(new LengthFieldPrepender(4));
							ch.pipeline().addLast(new ServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 1024);
			ChannelFuture channel = serverBoot.bind(port).sync();
			STARTED = true;
			channel.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
