package com.xjb.rpc.rpccommon.utils.config;

import com.xjb.rpc.rpccommon.registry.zk.utils.CuratorUtils;
import com.xjb.rpc.rpccommon.utils.threadpool.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: xjb-rpc
 * @description: 当系统关闭的时候，可以作如下操作：清理服务
 * @author: karl.xu
 * @create: 2020-10-14 16:11
 */
@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CuratorUtils.clearRegistry(CuratorUtils.getZkClient());
            ThreadPoolFactoryUtils.shutDownAllThreadPool();
        }));
    }
}
