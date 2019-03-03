package com.rpc.zw.netty.protocal;

import com.rpc.zw.netty.constant.ProtocalVersion;
import com.rpc.zw.netty.exception.RPCException;
import com.rpc.zw.netty.utility.HexUtility;

/**
 * basic协议的解析器
 * 
 * @author ZZ
 *
 */
public class ProtocalParserBasic extends AbstractParser {

	@Override
	public ProtocalInfoBasic parsing(byte[] data) throws RPCException {
		checkLength(data, MIN_DATA_BASE_LENGTH);

		int subscript = 0;

		int length = getInt(data, subscript);
		subscript += 4;

		// check magicNumber
		checkMagicNumber(data, subscript);
		subscript += 4;

		// protocal version,high in the former
		String protocalVersion = buildProtocalVersion(data, subscript);
		subscript += 2;

		byte msgType = parseMsgType(data, subscript);
		subscript += 1;

		byte serializationOption = parseSerialize(data, subscript);
		subscript += 1;

		short serviceId = getShort(data, subscript);
		subscript += 2;

		byte serviceVersion = getByte(data, subscript);
		subscript += 1;

		int requestId = getInt(data, subscript);
		subscript += 4;

		byte encryptionKeyIndex = getByte(data, subscript);
		subscript += 1;

		byte algorithm = getByte(data, subscript);
		subscript += 1;

		// byte responseCode = getByte(data, subscript); 服务端用不到此协议头,直接跳过
		subscript += 1;

		// 报文体
		int contentLength = data.length - subscript;
		byte[] content = null;
		if (contentLength > 0) {
			content = getByteArr(data, subscript, contentLength);
		}
		ProtocalInfoBasic protocal = new ProtocalInfoBasic();
		protocal.setProtocalLength(length);
		protocal.setProtocalVersion(protocalVersion);
		protocal.setMsgType(msgType);
		protocal.setSerializationOption(serializationOption);
		protocal.setServiceId(serviceId);
		protocal.setServiceVersion(serviceVersion);
		protocal.setRequestId(requestId);
		protocal.setEncryptionKeyIndex(encryptionKeyIndex);
		protocal.setAlgorithm(algorithm);
		protocal.setContent(content);
		return protocal;
	}

	public void checkMagicNumber(byte[] data, int subscript) throws RPCException {
		byte[] magicNumber = new byte[4];
		System.arraycopy(data, subscript, magicNumber, 0, 4);
		for (int i = 0; i < 4; i++) {
			if (magicNumber[i] != MAGIC_NUMBER_BYTEARR[i]) {
				throw new RPCException("msgicNumber error " + HexUtility.bytesToHex(magicNumber));
			}
		}
	}

	public String buildProtocalVersion(byte[] data, int subscript) throws RPCException {
		byte[] ptVersion = new byte[2];
		System.arraycopy(data, subscript, ptVersion, 0, 2);
		byte mainVersion = ptVersion[0];
		byte secondVersion = ptVersion[1];

		StringBuilder str = new StringBuilder();
		str.append(mainVersion);
		str.append(".");
		str.append(secondVersion);
		String version = str.toString();
		ProtocalVersion[] values = ProtocalVersion.values();
		boolean checkRsl = false;
		for (ProtocalVersion vs : values) {
			if (vs.getVersion().equals(version)) {
				checkRsl = true;
			}
		}
		if (!checkRsl) {
			throw new RPCException("protocal version error " + version);
		}
		return version;
	}

	public byte parseMsgType(byte[] data, int subscript) throws RPCException {
		byte msgType = getByte(data, subscript);
		if (msgType != (byte) 0x00 && msgType != (byte) 0x02 && msgType != (byte) 0x03) {
			throw new RPCException("invalid msgType " + msgType);
		}
		return msgType;
	}

	public byte parseSerialize(byte[] data, int subscript) throws RPCException {
		byte serializationOption = getByte(data, subscript);
		if (serializationOption != 0x00 && serializationOption != 0x01) {
			throw new RPCException("invalid msgType " + serializationOption);
		}
		return serializationOption;
	}
}
