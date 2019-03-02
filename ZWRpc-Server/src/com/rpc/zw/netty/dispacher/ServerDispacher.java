package com.rpc.zw.netty.dispacher;

import org.slf4j.Logger;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.context.ClientInfo;
import com.rpc.zw.netty.context.ServiceRequest;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.exception.RPCException;
import com.rpc.zw.netty.manager.ClientManager;
import com.rpc.zw.netty.manager.ContextManager;
import com.rpc.zw.netty.manager.ProtocalManager;
import com.rpc.zw.netty.protocal.ProtocalInfoBasic;
import com.rpc.zw.netty.utility.LogUtility;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class ServerDispacher implements Runnable, ProtocalConstant {

	private String clientId;

	private byte[] data;

	private Logger log = LogUtility.rpcLog();

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
			try {
				serviceRoute.routWithFilter(request, response);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			// ����one-way��Ϣ(����ҪӦ��)
			if (protocalInfo.getMsgType() == MSG_TYPE_ONE_WAY) {
				return;
			}

			// �ɹ�Ӧ����Ϣ
			byte[] responseData = ProtocalManager.getBasicBuild().buildResponse(protocalInfo, response);
			ClientInfo client = ClientManager.getClient(clientId);
			if (client == null) {
				log.warn("�ɹ�Ӧ����Ϣʱ,ͨ�����ر�! clientIp:{},url:{}", clientIp, request.getRequestUrl());
			}
			Channel channel = client.getChannel();
			ByteBuf byteBuf = channel.alloc().buffer(responseData.length);
			byteBuf.writeBytes(responseData);
			channel.writeAndFlush(byteBuf);
		} catch (Exception e) {
			log.warn("��Ϣ�ַ�ʱ,δ֪��������� {}", e.getMessage());
		}
	}

	private void closeChannel() {
		ClientInfo client = ClientManager.getClient(clientId);
		if (client != null) {
			Channel channel = client.getChannel();
			if (channel != null) {
				channel.close();
			}
		}
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	private String getClientIp() {
		ClientInfo client = ClientManager.getClient(clientId);
		if (client == null) {
			log.warn("��ȡclientIp��ʱ��,ͨ�����ر�");
			return null;
		}
		return client.getClientIp();
	}
}
