package com.rpc.zw.netty.dispacher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.context.RouteInfo;
import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.filter.IRequestFilterChain;
import com.rpc.zw.netty.filter.IResponseFilterChain;

public abstract class AbstractServiceRoute implements IServiceRoute, ProtocalConstant {
	protected final Map<String, RouteInfo> serviceRoute = new HashMap<>();
	protected final List<IRequestFilterChain> requestFilterChain = new ArrayList<>();
	protected final List<IResponseFilterChain> responseFilterChain = new ArrayList<>();

	public abstract void init() throws Exception;

	protected abstract void doFilterBefore(ServiceRequest request, ServiceResponse response) throws Exception;

	protected abstract void doFilterAfter(ServiceRequest request, ServiceResponse response) throws Exception;

	@Override
	public void serviceRout(ServiceRequest request, ServiceResponse response) throws Exception {
		String requestUrl = request.getRequestUrl();
		RouteInfo routeInfo = serviceRoute.get(requestUrl);
		if (routeInfo == null) {
			throw new Exception("url not found");
		}
		Method method = routeInfo.getMethod();
		if (method == null) {
			throw new Exception("url not found");
		}
		try {
			method.invoke(routeInfo.getFacade(), request, response);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void routWithFilter(ServiceRequest request, ServiceResponse response) throws Exception {
		doFilterBefore(request, response);
		serviceRout(request, response);
		doFilterAfter(request, response);
	}
}
