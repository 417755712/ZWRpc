package com.rpc.zw.netty.dispacher;

import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;

public interface IServiceRoute {
	// ����·��
	void serviceRout(ServiceRequest request, ServiceResponse response) throws Exception;

	// ��ǰ�ù�����������·��
	void routWithFilter(ServiceRequest request, ServiceResponse response) throws Exception;

	void init() throws Exception;
}
