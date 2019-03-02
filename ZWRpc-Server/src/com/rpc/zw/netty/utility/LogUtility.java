package com.rpc.zw.netty.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtility {
	private static final String PRC_LOG_NAME = "zw.rpc.netty";

	private LogUtility() {
	}

	public static Logger rpcLog() {
		return LoggerFactory.getLogger(PRC_LOG_NAME);
	}
}
