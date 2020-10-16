package com.xjb.rpc.server;

import com.xjb.rpc.rpccommon.annons.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RpcScan(basePackage = {"com.xjb.rpc"})
@SpringBootApplication
public class RpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }

}
