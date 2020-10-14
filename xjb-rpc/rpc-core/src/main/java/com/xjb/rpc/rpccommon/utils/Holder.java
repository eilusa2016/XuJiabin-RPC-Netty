package com.xjb.rpc.rpccommon.utils;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-14 14:07
 */
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
