package com.xjb.rpc.rpccommon.entity;

import lombok.*;

/**
 * @program: xjb-rpc
 * @description: rpc服务的属性
 * @author: karl.xu
 * @create: 2020-10-14 13:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceProperties {
    /**
     * service version
     */
    private String version;
    /**
     * when the interface has multiple implementation classes, distinguish by group
     */
    private String group;
    private String serviceName;

    public String toRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }
}
