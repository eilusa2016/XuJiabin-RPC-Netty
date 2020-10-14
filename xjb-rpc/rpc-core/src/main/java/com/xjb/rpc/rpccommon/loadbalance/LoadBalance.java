package com.xjb.rpc.rpccommon.loadbalance;

import java.util.List;

public interface LoadBalance {

    /**
     * 从服务列表 选择一个地址
     *
     * @param serviceAddresses Service address list
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceAddresses);
}
