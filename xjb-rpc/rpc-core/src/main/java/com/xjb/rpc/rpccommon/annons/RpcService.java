package com.xjb.rpc.rpccommon.annons;

import java.lang.annotation.*;

/**
 * 注解RPC服务实现类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    /**
     * RPC服务实现的版本号 ，默认空的字符串
     */
    String version() default "";

    /**
     * 服务的分组，区分同一个服务不同的实现
     */
    String group() default "";
}
