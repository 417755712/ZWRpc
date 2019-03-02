package com.rpc.zw.test;

import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.filter.IRequestFilterChain;

public class FilterTest5 implements IRequestFilterChain {

	@Override
	public void doFilterBefore(ServiceRequest request) throws Exception {
		System.out.println("I'M filter5 我的优先级为" + 2);
	}

	@Override
	public int getPriority() {
		return 2;
	}
}
