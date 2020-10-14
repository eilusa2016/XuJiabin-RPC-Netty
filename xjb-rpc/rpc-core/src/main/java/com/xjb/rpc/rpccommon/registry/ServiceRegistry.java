package com.xjb.rpc.rpccommon.registry;

import com.xjb.rpc.rpccommon.extension.SPI;

import java.net.InetSocketAddress;

/**
 * 服务注册
 */
@SPI
public interface ServiceRegistry {
    /**
     * register service
     * 注册服务  在zK生成节点
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
