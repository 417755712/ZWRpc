package com.rpc.zw.netty.context;

public class ServiceResponse {
	// 返回报文序列化方式 0:protobuf 1:json 默认json
	private byte serializationOption = (byte) 0x01;
	// 秘钥索引(暂时没有此功能)
	private byte encryptionKeyIndex = (byte) 0x00;
	// 加密方式(暂时没有此功能)
	private byte algorithmIndicator = (byte) 0x00;

	private byte[] data;

	public void setSerializeJson() {
		this.serializationOption = (byte) 0x01;
	}

	public void setSerializeProtobuf() {
		this.serializationOption = (byte) 0x00;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte getSerializationOption() {
		return serializationOption;
	}

	public byte getEncryptionKeyIndex() {
		return encryptionKeyIndex;
	}

	public byte getAlgorithmIndicator() {
		return algorithmIndicator;
	}

	public byte[] getData() {
		return data;
	}
}
