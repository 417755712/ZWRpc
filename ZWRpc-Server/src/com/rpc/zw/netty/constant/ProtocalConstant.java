package com.rpc.zw.netty.constant;

public interface ProtocalConstant {
	int MIN_DATA_BASE_LENGTH = 22; // protocal base version need minimum length
	byte[] MAGIC_NUMBER_BYTEARR = new byte[] { (byte) 90, (byte) 87, (byte) 122, (byte) 119 };

	byte MSG_TYPE_RESPONSE = (byte) 0x01;
	byte MSG_TYPE_ONE_WAY = (byte) 0x02;
	byte MSG_TYPE_HEART_BEAT = (byte) 0x03;

	byte SERIALIZE_PROTOBUF = (byte) 0x00;
	byte SERIALIZE_JSON = (byte) 0x01;

	byte RESPONSE_CODE_SUCCESS = (byte) 0x00;
	byte RESPONSE_CODE_ERROR = (byte) 0x01;
	
	String CHARSET = "UTF-8";
}
