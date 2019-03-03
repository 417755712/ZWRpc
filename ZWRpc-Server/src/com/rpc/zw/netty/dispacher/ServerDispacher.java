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

			// 解析协议
			ProtocalInfoBasic protocalInfo = null;
			try {
				protocalInfo = ProtocalManager.getBasicParser().parsing(data);
			} catch (RPCException e) {
				log.error("收到非法的协议请求 {}" + e.getMessage());
				closeChannel();
				return;
			}

			// 处理心跳协议
			if (protocalInfo.getMsgType() == MSG_TYPE_HEART_BEAT) {
				return;
			}
			String clientIp = getClientIp();
			if (clientIp == null) {
				return;
			}
			// 构建request
			ServiceRequest request = new ServiceRequest();
			request.setClientIp(clientIp);
			request.setProtocalLength(protocalInfo.getProtocalLength());
			request.setProtocalVersion(protocalInfo.getProtocalVersion());
			request.setSerializationOption(protocalInfo.getSerializationOption());
			request.setServiceId(protocalInfo.getServiceId());
			request.setServiceVersion(protocalInfo.getServiceVersion());
			request.setContent(protocalInfo.getContent());

			// 构建response
			ServiceResponse response = new ServiceResponse();

			// 服务分发
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

			// 处理one-way信息(不需要应答)
			if (protocalInfo.getMsgType() == MSG_TYPE_ONE_WAY) {
				return;
			}
			// 发送应答消息(成功/失败)
			if (routeSuccsee) {
				responseSuccess(protocalInfo, response);
			} else {
				responseError(protocalInfo, response, errorMsg);
			}

		} catch (Exception e) {
			log.warn("消息分发时,未知的意外错误 {}", e.getMessage());
		}
	}
}
