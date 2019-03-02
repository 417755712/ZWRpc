package com.rpc.zw.netty.dispacher;

import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;

public interface IServiceRoute {
	// 服务路由
	void serviceRout(ServiceRequest request, ServiceResponse response) throws Exception;

	// 从前置过滤器到服务路由
	void routWithFilter(ServiceRequest request, ServiceResponse response) throws Exception;

	void init() throws Exception;
}
