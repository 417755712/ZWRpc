package com.rpc.zw.netty.protocal;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.exception.RPCException;
import com.rpc.zw.netty.protocal.inf.ProtocalParserInf;
import com.rpc.zw.netty.utility.ByteUtility;

public abstract class AbstractParser implements ProtocalParserInf, ProtocalConstant {

	protected byte getByte(byte[] data, int subscript) {
		return data[subscript];
	}

	protected byte[] getByteArr(byte[] data, int subscript, int length) {
		byte[] desData = new byte[length];
		System.arraycopy(data, subscript, desData, 0, length);
		return desData;
	}

	protected short getShort(byte[] data, int subscript) {
		byte[] desData = new byte[2];
		System.arraycopy(data, subscript, desData, 0, 2);
		return ByteUtility.byteArrToShort(desData);
	}

	protected int getInt(byte[] data, int subscript) {
		byte[] desData = new byte[4];
		System.arraycopy(data, subscript, desData, 0, 4);
		return ByteUtility.byteArrayToInt(desData);
	}

	protected void checkLength(byte[] data, int miniumLength) throws RPCException {
		if (data == null || data.length < miniumLength) {
			throw new RPCException("length too short " + data.length);
		}
	}
}
