package com.rpc.zw.netty.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rpc.zw.netty.context.ClientInfo;

public final class ClientManager {
	
	private final static Map<String, ClientInfo> clientManager = new ConcurrentHashMap<>();

	public static ClientInfo getClient(String clientId) {
		return clientManager.get(clientId);
	}

	public static void addClient(String clientId, ClientInfo clientInfo) {
		clientManager.put(clientId, clientInfo);
	}

	public static void removeClient(String clientId) {
		clientManager.remove(clientId);
	}
	
}
