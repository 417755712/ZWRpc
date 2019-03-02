package com.rpc.zw.netty.dispacher;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;

import com.rpc.zw.netty.annotation.ServiceEntry;
import com.rpc.zw.netty.context.RouteInfo;
import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.facade.IFacade;
import com.rpc.zw.netty.filter.IRequestFilterChain;
import com.rpc.zw.netty.filter.IResponseFilterChain;
import com.rpc.zw.netty.helper.ProtocalHelper;
import com.rpc.zw.netty.manager.ContextManager;

public class ServiceRoute extends AbstractServiceRoute {

	/**
	 * 初始化路由url 和 filter
	 * 
	 * @throws Exception
	 */
	@Override
	public void init() throws Exception {
		ApplicationContext ctx = ContextManager.getSpringCtx();
		initRoute(ctx);
		initFilter(ctx);
	}

	@Override
	protected void doFilterBefore(ServiceRequest request, ServiceResponse response) throws Exception {
		for (IRequestFilterChain chain : requestFilterChain) {
			chain.doFilterBefore(request);
		}
	}

	@Override
	protected void doFilterAfter(ServiceRequest request, ServiceResponse response) throws Exception {
		for (IResponseFilterChain chain : responseFilterChain) {
			chain.doFilterBefore(request);
		}
	}

	private void initRoute(ApplicationContext ctx) throws Exception {
		Map<String, IFacade> facades = ctx.getBeansOfType(IFacade.class);
		if (facades == null) {
			return;
		}
		for (Entry<String, IFacade> entry : facades.entrySet()) {
			IFacade facade = entry.getValue();
			Method[] methods = facade.getClass().getMethods();
			if (methods == null || methods.length == 0) {
				continue;
			}
			for (Method method : methods) {
				ServiceEntry serviceEntry = method.getAnnotation(ServiceEntry.class);
				if (serviceEntry == null) {
					continue;
				}
				short serviceId = serviceEntry.serviceId();
				byte version = serviceEntry.version();

				String routeId = ProtocalHelper.buildServiceUrl(serviceId, version);
				RouteInfo routeInfo = serviceRoute.get(routeId);
				if (routeInfo != null) {
					Method routeMethod = routeInfo.getMethod();
					if (routeMethod != null) {
						throw new Exception("重复的路由映射路径:" + serviceId + "版本号:" + version);
					}
				}
				RouteInfo info = new RouteInfo();
				info.setFacade(facade);
				info.setMethod(method);

				serviceRoute.put(routeId, info);
			}
		}
	}

	private void initFilter(ApplicationContext ctx) {
		// 初始化前置过滤器
		Map<String, IRequestFilterChain> requestFilters = ctx.getBeansOfType(IRequestFilterChain.class);
		for (Map.Entry<String, IRequestFilterChain> entry : requestFilters.entrySet()) {
			IRequestFilterChain filter = entry.getValue();
			addReqFilter(filter);
		}

		// 初始化后置过滤器
		Map<String, IResponseFilterChain> responseFilters = ctx.getBeansOfType(IResponseFilterChain.class);
		for (Map.Entry<String, IResponseFilterChain> entry : responseFilters.entrySet()) {
			IResponseFilterChain filter = entry.getValue();
			addRspFilter(filter);
		}
	}

	private void addReqFilter(IRequestFilterChain requestFilter) {
		synchronized (requestFilterChain) {
			int index = 0;
			if (!requestFilterChain.contains(requestFilter)) {
				for (IRequestFilterChain chain : requestFilterChain) {
					if (requestFilter.getPriority() <= chain.getPriority()) {
						index++;
					} else {
						break;
					}
				}
				requestFilterChain.add(index, requestFilter);
			}
		}
	}

	private void addRspFilter(IResponseFilterChain responseFilter) {
		synchronized (responseFilterChain) {
			int index = 0;
			if (!responseFilterChain.contains(responseFilter)) {
				for (IResponseFilterChain chain : responseFilterChain) {
					if (responseFilter.getPriority() <= chain.getPriority()) {
						index++;
					} else {
						break;
					}

				}
				responseFilterChain.add(index, responseFilter);
			}
		}
	}
}
