package com.rpc.zw.netty.constant;

public enum ProtocalVersion {

	V_0_1("0.1");

	private String version;

	private ProtocalVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
}
