package com.rpc.zw.netty.context;

import io.netty.channel.Channel;

public class ClientInfo {
	private String clinetId;
	private String clientIp;
	private Channel channel;

	public String getClinetId() {
		return clinetId;
	}

	public void setClinetId(String clinetId) {
		this.clinetId = clinetId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
