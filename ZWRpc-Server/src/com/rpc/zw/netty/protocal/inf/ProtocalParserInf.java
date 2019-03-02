package com.rpc.zw.netty.protocal.inf;

import com.rpc.zw.netty.exception.RPCException;
import com.rpc.zw.netty.protocal.ProtocalInfoBasic;

/**
 * protocal parsing interface
 * 
 * @author ZZ
 *
 */
public interface ProtocalParserInf {
	ProtocalInfoBasic parsing(byte[] data) throws RPCException;
}
