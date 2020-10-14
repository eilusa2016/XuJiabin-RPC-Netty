package com.xjb.rpc.rpccommon.utils.system;

/**
 * @program: xjb-rpc
 * @description: 运行时系统工具
 * @author: karl.xu
 * @create: 2020-10-14 16:08
 */
public class RuntimeUtil {
    /**
     * 获取系统的CPU的核数
     * @return
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
