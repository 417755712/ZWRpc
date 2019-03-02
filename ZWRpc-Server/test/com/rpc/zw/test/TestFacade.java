package com.rpc.zw.test;

import com.rpc.zw.netty.annotation.ServiceEntry;
import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.facade.IFacade;

public class TestFacade implements IFacade {

	@ServiceEntry(serviceId = (short) 0x0001)
	public void test1(ServiceRequest request, ServiceResponse response) {
		
	}
}
