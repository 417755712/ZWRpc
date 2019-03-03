package com.rpc.zw.netty.dispacher;

import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.exception.RPCException;
import com.rpc.zw.netty.manager.ContextManager;
import com.rpc.zw.netty.manager.ProtocalManager;
import com.rpc.zw.netty.protocal.ProtocalInfoBasic;

public class ServerDispacher extends AbstractServerDispacher {

	@Override
	public void run() {
		try {

			// ����Э��
			ProtocalInfoBasic protocalInfo = null;
			try {
				protocalInfo = ProtocalManager.getBasicParser().parsing(data);
			} catch (RPCException e) {
				log.error("�յ��Ƿ���Э������ {}" + e.getMessage());
				closeChannel();
				return;
			}

			// ��������Э��
			if (protocalInfo.getMsgType() == MSG_TYPE_HEART_BEAT) {
				return;
			}
			String clientIp = getClientIp();
			if (clientIp == null) {
				return;
			}
			// ����request
			ServiceRequest request = new ServiceRequest();
			request.setClientIp(clientIp);
			request.setProtocalLength(protocalInfo.getProtocalLength());
			request.setProtocalVersion(protocalInfo.getProtocalVersion());
			request.setSerializationOption(protocalInfo.getSerializationOption());
			request.setServiceId(protocalInfo.getServiceId());
			request.setServiceVersion(protocalInfo.getServiceVersion());
			request.setContent(protocalInfo.getContent());

			// ����response
			ServiceResponse response = new ServiceResponse();

			// ����ַ�
			IServiceRoute serviceRoute = ContextManager.getServiceRoute();
			boolean routeSuccsee = true;
			String errorMsg = "";
			try {
				serviceRoute.routWithFilter(request, response);
			} catch (Exception e) {
				routeSuccsee = false;
				errorMsg = e.getMessage();
				log.debug("route failed : {}" + errorMsg);
				return;
			}

			// ����one-way��Ϣ(����ҪӦ��)
			if (protocalInfo.getMsgType() == MSG_TYPE_ONE_WAY) {
				return;
			}
			// ����Ӧ����Ϣ(�ɹ�/ʧ��)
			if (routeSuccsee) {
				responseSuccess(protocalInfo, response);
			} else {
				responseError(protocalInfo, response, errorMsg);
			}

		} catch (Exception e) {
			log.warn("��Ϣ�ַ�ʱ,δ֪��������� {}", e.getMessage());
		}
	}
}
