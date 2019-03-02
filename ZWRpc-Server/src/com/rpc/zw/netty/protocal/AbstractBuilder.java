package com.rpc.zw.netty.protocal;

import com.rpc.zw.netty.constant.ProtocalConstant;
import com.rpc.zw.netty.protocal.inf.ProtocalBuildInf;
import com.rpc.zw.netty.utility.ByteUtility;

public abstract class AbstractBuilder implements ProtocalBuildInf, ProtocalConstant {

	protected void putByte(byte[] data, byte value, int subscript) {
		data[subscript] = value;
	}

	protected void putByteArr(byte[] data, byte[] value, int subscript) {
		System.arraycopy(value, 0, data, subscript, value.length);
	}

	protected void putInt(byte[] data, int value, int subscript) {
		byte[] lengthArr = ByteUtility.intToByteArray(value);
		System.arraycopy(lengthArr, 0, data, subscript, 4);
	}

	protected void putShort(byte[] data, short value, int subscript) {
		byte[] shortByteArr = ByteUtility.shortToByteArr(value);
		System.arraycopy(shortByteArr, 0, data, subscript, 2);
	}
}
