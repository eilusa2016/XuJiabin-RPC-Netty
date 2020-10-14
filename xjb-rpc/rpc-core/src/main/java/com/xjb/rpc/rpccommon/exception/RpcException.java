package com.xjb.rpc.rpccommon.exception;

import com.xjb.rpc.rpccommon.enums.RpcErrorMessageEnum;

/**
 * @program: xjb-rpc
 * @description: RPC服务抛出的异常
 * @author: karl.xu
 * @create: 2020-10-14 14:02
 */
public class RpcException extends RuntimeException  {

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
