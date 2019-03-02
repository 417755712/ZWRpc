package com.rpc.zw.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpc.zw.netty.context.RPCServerContext;

public class ServerTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		RPCServerContext context = new RPCServerContext();
		context.serverStart(ctx, 80);
	}
}
