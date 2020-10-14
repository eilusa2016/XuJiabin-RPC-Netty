package com.xjb.rpc.rpccommon.rpc.dto;

import com.xjb.rpc.rpccommon.entity.RpcServiceProperties;
import lombok.*;

import java.io.Serializable;

/**
 * @program: xjb-rpc
 * @description: RPC请求的参数类
 * @author: karl.xu
 * @create: 2020-10-14 13:59
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public RpcServiceProperties toRpcProperties() {
        return RpcServiceProperties.builder().serviceName(this.getInterfaceName())
                .version(this.getVersion())
                .group(this.getGroup()).build();
    }
}
