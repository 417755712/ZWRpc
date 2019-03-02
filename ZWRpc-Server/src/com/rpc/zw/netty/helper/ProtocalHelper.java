package com.rpc.zw.netty.helper;

public class ProtocalHelper {

	private ProtocalHelper() {
	}

	/**
	 * 构建路由请求路径
	 * 
	 * @param serviceId
	 * @param version
	 * @return
	 */
	public static String buildServiceUrl(short serviceId, byte version) {
		return serviceId + "|" + version;
	}
}
