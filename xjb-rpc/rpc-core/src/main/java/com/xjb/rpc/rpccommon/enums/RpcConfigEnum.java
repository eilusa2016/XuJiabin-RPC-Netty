package com.xjb.rpc.rpccommon.enums;

public enum RpcConfigEnum {

    RPC_CONFIG_PATH("application.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;


    RpcConfigEnum(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

}
