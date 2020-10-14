package com.xjb.rpc.rpccommon.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @program: xjb-rpc
 * @description: 随机的实现
 * @author: karl.xu
 * @create: 2020-10-14 15:49
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
