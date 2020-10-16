package com.xjb.rpc.server.service.impl;

import com.xjb.rpc.api.pojo.Hello;
import com.xjb.rpc.api.services.HelloService;
import com.xjb.rpc.rpccommon.annons.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: xjb-rpc
 * @description: RPC服务的提供者
 * @author: karl.xu
 * @create: 2020-10-16 11:32
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
//@Component
public class HelloServiceImpl implements HelloService {

    public  HelloServiceImpl(){
        System.out.println("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }


}
