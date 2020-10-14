package com.xjb.rpc.rpccommon.rpc;

import com.xjb.rpc.rpccommon.rpc.dto.RpcRequest;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-14 13:51
 */
public interface ClientTransport {
    /**
     * 发送RPC请求 并且获取结果
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
