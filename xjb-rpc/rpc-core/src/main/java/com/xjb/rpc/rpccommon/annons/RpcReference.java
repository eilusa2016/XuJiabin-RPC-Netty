package com.xjb.rpc.rpccommon.annons;

import java.lang.annotation.*;

/**
 * 需要注解RPC服务依赖的接口
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    /**
     * RPC服务实现的版本号 ，默认空的字符串
     */
    String version() default "";

    /**
     *  服务的分组，区分同一个服务不同的实现
     */
    String group() default "";
}
