package com.rpc.zw.netty.helper;

public class ProtocalHelper {

	private ProtocalHelper() {
	}

	/**
	 * ����·������·��
	 * 
	 * @param serviceId
	 * @param version
	 * @return
	 */
	public static String buildServiceUrl(short serviceId, byte version) {
		return serviceId + "|" + version;
	}
}
