package com.rpc.zw.netty.manager;

import org.springframework.context.ApplicationContext;

import com.rpc.zw.netty.dispacher.IServiceRoute;
import com.rpc.zw.netty.dispacher.ServiceRoute;

/**
 * 上下文管理类
 * 
 * @author ZZ
 *
 */
public class ContextManager {
	// spring config
	private static ApplicationContext springCtx;
	// service route
	private static IServiceRoute serviceRoute = new ServiceRoute();

	public static ApplicationContext getSpringCtx() {
		return springCtx;
	}

	public static void setSpringCtx(ApplicationContext ctx) {
		if (springCtx == null) {
			synchronized (ContextManager.class) {
				if (springCtx == null) {
					springCtx = ctx;
				}
			}
		}
	}

	public static IServiceRoute getServiceRoute() {
		return serviceRoute;
	}
}
