package com.rpc.zw.netty.filter;

import com.rpc.zw.netty.context.ServiceRequest;

/**
 * ·��֮ǰ�ᴥ���Ĺ��������������������� ��Ҫʵ�ִ���,���������FilterCenter����addһ��������
 * 
 * @author ZZ
 *
 */
public interface IRequestFilterChain {

	void doFilterBefore(ServiceRequest request) throws Exception;

	/**
	 * ����Խ�� ���ȼ�Խ��
	 * 
	 * @param priority
	 */
	int getPriority();
}
