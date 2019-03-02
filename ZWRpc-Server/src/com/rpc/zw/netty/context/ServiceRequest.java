package com.rpc.zw.netty.context;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.helper.ProtocalHelper;

public class ServiceRequest implements ProtocalConstant {
	private String clientIp;
	private int protocalLength;
	private String protocalVersion;
	private byte serializationOption;
	private short serviceId;
	private byte serviceVersion;
	private byte[] content;

	public String getRequestUrl() {
		return ProtocalHelper.buildServiceUrl(serviceId, serviceVersion);
	}

	/**
	 * 序列化方式是否是json
	 * 
	 * @return
	 */
	public boolean isSerializeJson() {
		return serializationOption == SERIALIZE_JSON;
	}

	/**
	 * 序列化方式是否是protobuf
	 * 
	 * @return
	 */
	public boolean isSerializeProtobuf() {
		return serializationOption == SERIALIZE_PROTOBUF;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * 所有报文总长度(包含报文头)
	 * 
	 * @return
	 */
	public int getProtocalLength() {
		return protocalLength;
	}

	public void setProtocalLength(int protocalLength) {
		this.protocalLength = protocalLength;
	}

	/**
	 * 协议版本
	 * 
	 * @return
	 */
	public String getProtocalVersion() {
		return protocalVersion;
	}

	public void setProtocalVersion(String protocalVersion) {
		this.protocalVersion = protocalVersion;
	}

	public byte getSerializationOption() {
		return serializationOption;
	}

	public void setSerializationOption(byte serializationOption) {
		this.serializationOption = serializationOption;
	}

	public short getServiceId() {
		return serviceId;
	}

	public void setServiceId(short serviceId) {
		this.serviceId = serviceId;
	}

	public byte getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(byte serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
