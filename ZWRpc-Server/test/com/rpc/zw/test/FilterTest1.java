package com.rpc.zw.test;

import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.filter.IRequestFilterChain;

public class FilterTest1 implements IRequestFilterChain {

	@Override
	public void doFilterBefore(ServiceRequest request) throws Exception {
		System.out.println("I'M filter1 �ҵ����ȼ�Ϊ" + 5);
	}

	@Override
	public int getPriority() {
		return 5;
	}
}
