package com.rpc.zw.netty.dispacher;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.context.ClientInfo;
import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.manager.ClientManager;
import com.rpc.zw.netty.manager.ProtocalManager;
import com.rpc.zw.netty.protocal.ProtocalInfoBasic;
import com.rpc.zw.netty.utility.LogUtility;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public abstract class AbstractServerDispacher implements Runnable, ProtocalConstant {
	protected String clientId;

	protected byte[] data;

	protected Logger log = LogUtility.rpcLog();

	/**
	 * Ӧ��ɹ�����Ϣ
	 * 
	 * @param protocalInfo
	 * @param response
	 */
	protected void responseSuccess(ProtocalInfoBasic protocalInfo, ServiceResponse response) {
		ClientInfo client = ClientManager.getClient(clientId);
		if (client == null) {
			log.warn("��Ϣ����ɹ�Ӧ��ʱ,ͨ�����ر�! ");
			return;
		}
		response.setResponseCode(RESPONSE_CODE_SUCCESS);
		byte[] responseData = ProtocalManager.getBasicBuild().buildResponse(protocalInfo, response);
		Channel channel = client.getChannel();
		ByteBuf byteBuf = channel.alloc().buffer(data.length);
		byteBuf.writeBytes(responseData);
		channel.writeAndFlush(byteBuf);
	}

	/**
	 * Ӧ��ʧ�ܵ���Ϣ(��Ϣ�����Ƿ��ڱ������ﴫ��� UTF-8���͵�String)
	 * 
	 * @param protocalInfo
	 * @param response
	 */
	protected void responseError(ProtocalInfoBasic protocalInfo, ServiceResponse response, String errorMsg) {
		ClientInfo client = ClientManager.getClient(clientId);
		if (client == null) {
			log.warn("��Ϣ����ɹ�Ӧ��ʱ,ͨ�����ر�! ");
			return;
		}
		response.setResponseCode(RESPONSE_CODE_SUCCESS);
		try {
			response.setData(errorMsg.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
		}
		byte[] responseData = ProtocalManager.getBasicBuild().buildResponse(protocalInfo, response);
		Channel channel = client.getChannel();
		ByteBuf byteBuf = channel.alloc().buffer(data.length);
		byteBuf.writeBytes(responseData);
		channel.writeAndFlush(byteBuf);
	}

	protected void closeChannel() {
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

	protected String getClientIp() {
		ClientInfo client = ClientManager.getClient(clientId);
		if (client == null) {
			log.warn("��ȡclientIp��ʱ��,ͨ�����ر�");
			return null;
		}
		return client.getClientIp();
	}
}
