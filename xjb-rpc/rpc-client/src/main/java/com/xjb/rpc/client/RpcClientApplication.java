package com.xjb.rpc.client;

import com.xjb.rpc.rpccommon.annons.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@RpcScan(basePackage = {"com.xjb.rpc"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }

}
