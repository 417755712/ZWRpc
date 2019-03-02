package com.rpc.zw.netty.protocal;

/**
 * 
 * basic协议,后续所有的协议版本都要基于此协议版本拓展
 * 
 * @author ZZ
 *
 */
public class ProtocalInfoBasic {
	private int protocalLength;
	private String protocalVersion;
	private byte msgType;
	private byte serializationOption;
	private short serviceId;
	private byte serviceVersion;
	private int requestId;
	private byte encryptionKeyIndex;
	private byte algorithm;
	private byte[] content;

	public int getProtocalLength() {
		return protocalLength;
	}

	public void setProtocalLength(int protocalLength) {
		this.protocalLength = protocalLength;
	}

	public String getProtocalVersion() {
		return protocalVersion;
	}

	public void setProtocalVersion(String protocalVersion) {
		this.protocalVersion = protocalVersion;
	}

	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
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

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public byte getEncryptionKeyIndex() {
		return encryptionKeyIndex;
	}

	public void setEncryptionKeyIndex(byte encryptionKeyIndex) {
		this.encryptionKeyIndex = encryptionKeyIndex;
	}

	public byte getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(byte algorithm) {
		this.algorithm = algorithm;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
