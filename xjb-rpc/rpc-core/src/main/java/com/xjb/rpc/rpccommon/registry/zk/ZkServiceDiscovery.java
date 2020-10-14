package com.xjb.rpc.rpccommon.registry.zk;

import com.xjb.rpc.rpccommon.enums.RpcErrorMessageEnum;
import com.xjb.rpc.rpccommon.exception.RpcException;
import com.xjb.rpc.rpccommon.loadbalance.LoadBalance;
import com.xjb.rpc.rpccommon.loadbalance.RandomLoadBalance;
import com.xjb.rpc.rpccommon.registry.ServiceDiscovery;
import com.xjb.rpc.rpccommon.registry.zk.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @program: xjb-rpc
 * @description: zookeeper服务发现
 * @author: karl.xu
 * @create: 2020-10-14 14:41
 */
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscovery() {
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
