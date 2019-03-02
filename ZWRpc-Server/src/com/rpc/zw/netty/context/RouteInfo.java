package com.rpc.zw.netty.context;

import java.lang.reflect.Method;

import com.rpc.zw.netty.facade.IFacade;

public class RouteInfo {
	private IFacade facade;
	private Method method;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public IFacade getFacade() {
		return facade;
	}

	public void setFacade(IFacade facade) {
		this.facade = facade;
	}
}
