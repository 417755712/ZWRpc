package com.rpc.zw.netty.utility;

public class ByteUtility {
	public static byte intToByte(int x) {
		return (byte) x;
	}

	public static int byteToInt(byte b) {
		return b & 0xFF;
	}

	public static int byteArrayToInt(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	/**
	 * 高位在前
	 * 
	 * @param b
	 * @param offset
	 * @return
	 */
	public static short byteArrToShort(byte[] b) {
		return (short) ((b[0] << 8 & 0xFF00) | (b[1] & 0x00FF));
	}

	/**
	 * 高位在前
	 * 
	 * @param x
	 * @return
	 */
	public static byte[] shortToByteArr(short s) {
		return new byte[] { (byte) (0x00FF & (s >> 8)), (byte) (0x00FF & s) };
	}
}
