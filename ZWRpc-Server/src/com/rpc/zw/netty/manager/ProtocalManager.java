package com.rpc.zw.netty.manager;

import com.rpc.zw.netty.protocal.ProtocalBuilderBasic;
import com.rpc.zw.netty.protocal.ProtocalParserBasic;

public class ProtocalManager {
	private static final ProtocalParserBasic basicParser = new ProtocalParserBasic();
	private static final ProtocalBuilderBasic basicBuild = new ProtocalBuilderBasic();

	public static ProtocalParserBasic getBasicParser() {
		return basicParser;
	}

	public static ProtocalBuilderBasic getBasicBuild() {
		return basicBuild;
	}
}
