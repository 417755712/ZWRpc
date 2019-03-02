package com.rpc.zw.netty.utility;

public class HexUtility {

	/**
	 * �ֽ�תʮ������
	 * 
	 * @param b
	 *            ��Ҫ����ת����byte�ֽ�
	 * @return ת�����Hex�ַ���
	 */
	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	/**
	 * �ֽ�����ת16����
	 * 
	 * @param bytes
	 *            ��Ҫת����byte����
	 * @return ת�����Hex�ַ���
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() < 2) {
				sb.append(0);
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * Hex�ַ���תbyte
	 * 
	 * @param inHex
	 *            ��ת����Hex�ַ���
	 * @return ת�����byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	/**
	 * hex�ַ���תbyte����
	 * 
	 * @param inHex
	 *            ��ת����Hex�ַ���
	 * @return ת�����byte������
	 */
	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// ����
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// ż��
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}
}
