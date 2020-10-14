package com.xjb.rpc.rpccommon.registry.zk;

import com.xjb.rpc.rpccommon.registry.ServiceRegistry;
import com.xjb.rpc.rpccommon.registry.zk.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * @program: xjb-rpc
 * @description: 服务注册的实现
 * @author: karl.xu
 * @create: 2020-10-14 14:42
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
