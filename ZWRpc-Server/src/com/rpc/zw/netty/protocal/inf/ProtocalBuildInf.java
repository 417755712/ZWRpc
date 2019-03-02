package com.rpc.zw.netty.protocal.inf;

import com.rpc.zw.netty.context.ServiceResponse;
import com.rpc.zw.netty.protocal.ProtocalInfoBasic;

public interface ProtocalBuildInf {
	byte[] buildResponse(ProtocalInfoBasic protocalInfo, ServiceResponse response);
}
