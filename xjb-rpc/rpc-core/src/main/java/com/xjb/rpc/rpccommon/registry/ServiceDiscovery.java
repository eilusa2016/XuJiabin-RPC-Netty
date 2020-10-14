package com.xjb.rpc.rpccommon.registry;

import com.xjb.rpc.rpccommon.extension.SPI;

import java.net.InetSocketAddress;

/**
 * 服务发现
 */
@SPI
public interface ServiceDiscovery {

    /**
     * 根据服务名称，在注册中心查询服务的地址
     *
     * @param rpcServiceName rpc service name
     * @return service address
     */
    InetSocketAddress lookupService(String rpcServiceName);

}
