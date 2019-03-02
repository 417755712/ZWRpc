package com.rpc.zw.netty.context;

public class ServiceResponse {
	// ���ر������л���ʽ 0:protobuf 1:json Ĭ��json
	private byte serializationOption = (byte) 0x01;
	// ��Կ����(��ʱû�д˹���)
	private byte encryptionKeyIndex = (byte) 0x00;
	// ���ܷ�ʽ(��ʱû�д˹���)
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
