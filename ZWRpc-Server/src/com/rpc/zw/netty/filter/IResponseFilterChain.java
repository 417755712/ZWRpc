package com.rpc.zw.netty.filter;

import com.rpc.zw.netty.context.ServiceRequest;

/**
 * ·��֮��ᴥ���Ĺ��������������������� ��Ҫʵ�ִ���,���������FilterCenter����addһ��������
 * 
 * @author ZZ
 *
 */
public interface IResponseFilterChain {

	void doFilterBefore(ServiceRequest request) throws Exception;

	/**
	 * ����Խ�� ���ȼ�Խ��
	 * 
	 * @param priority
	 */
	int getPriority();
}
