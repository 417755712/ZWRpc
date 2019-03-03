package com.rpc.zw.netty.protocal;

import com.rpc.zw.netty.context.ServiceResponse;

/**
 * basic协议构造器
 * 
 * @author ZZ
 *
 */
public class ProtocalBuilderBasic extends AbstractBuilder {

	private final byte[] BASE_PROTOCAL_VERSION = new byte[] { (byte) 0x00, (byte) 0x01 };

	@Override
	public byte[] buildResponse(ProtocalInfoBasic protocalInfo, ServiceResponse response) {

		byte[] content = response.getData();
		int rspLength = 0;
		if (content == null) {
			rspLength = MIN_DATA_BASE_LENGTH;
		} else {
			rspLength = MIN_DATA_BASE_LENGTH + content.length;
		}
		byte[] data = new byte[rspLength];

		// length
		int subscript = 0;
		putInt(data, rspLength, subscript);
		subscript += 4;

		// magicNumber
		putByteArr(data, MAGIC_NUMBER_BYTEARR, subscript);
		subscript += 4;

		// protocalVersion
		putByteArr(data, BASE_PROTOCAL_VERSION, subscript);
		subscript += 2;

		// msgType
		putByte(data, MSG_TYPE_RESPONSE, subscript);
		subscript += 1;

		// business serializationOption
		putByte(data, response.getSerializationOption(), subscript);
		subscript += 1;

		// serviceId
		short serviceId = protocalInfo.getServiceId();
		putShort(data, serviceId, subscript);
		subscript += 2;

		// serviceVersion
		putByte(data, protocalInfo.getServiceVersion(), subscript);
		subscript += 1;

		// requestId
		putInt(data, protocalInfo.getRequestId(), subscript);
		subscript += 4;

		// encryptionKeyIndex
		putByte(data, response.getEncryptionKeyIndex(), subscript);
		subscript += 1;

		// algorithmIndicator
		putByte(data, response.getAlgorithmIndicator(), subscript);
		subscript += 1;

		// responseCode
		putByte(data, response.getResponseCode(), subscript);
		subscript += 1;

		// data
		if (data.length >= subscript) {
			putByteArr(data, content, subscript);
		}

		return data;
	}
}
