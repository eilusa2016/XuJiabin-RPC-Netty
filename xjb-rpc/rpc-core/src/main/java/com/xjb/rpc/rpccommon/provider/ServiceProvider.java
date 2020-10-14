package com.xjb.rpc.rpccommon.provider;

import com.xjb.rpc.rpccommon.entity.RpcServiceProperties;

/**
 * @program: xjb-rpc
 * @description: 服务提供者
 * @author: karl.xu
 * @create: 2020-10-14 11:54
 */
public interface ServiceProvider {
    /**
     * @param service              service object
     * @param serviceClass         the interface class implemented by the service instance object
     * @param rpcServiceProperties service related attributes
     */
    void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties);

    /**
     * @param rpcServiceProperties service related attributes
     * @return service object
     */
    Object getService(RpcServiceProperties rpcServiceProperties);

    /**
     * @param service              service object
     * @param rpcServiceProperties service related attributes
     */
    void publishService(Object service, RpcServiceProperties rpcServiceProperties);

    /**
     * @param service service object
     */
    void publishService(Object service);
}
