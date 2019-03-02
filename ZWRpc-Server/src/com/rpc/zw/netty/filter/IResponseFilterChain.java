package com.rpc.zw.netty.filter;

import com.rpc.zw.netty.context.ServiceRequest;

/**
 * 路由之后会触发的过滤器链。想加入过滤器链 需要实现此类,并且向调用FilterCenter类中add一个过滤器
 * 
 * @author ZZ
 *
 */
public interface IResponseFilterChain {

	void doFilterBefore(ServiceRequest request) throws Exception;

	/**
	 * 数字越大 优先级越高
	 * 
	 * @param priority
	 */
	int getPriority();
}
