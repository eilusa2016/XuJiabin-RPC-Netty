package com.xjb.rpc.server.config;

import com.xjb.rpc.rpccommon.transport.netty.server.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-14 16:50
 */
@Configuration
public class ServerConfiguration {

    /**
     * 初始化启动
     * @return
     */
    @Bean
    public NettyServer getNettyServer() {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
        return nettyServer;
    }

}
