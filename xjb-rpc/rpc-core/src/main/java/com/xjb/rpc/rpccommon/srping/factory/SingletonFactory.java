package com.xjb.rpc.rpccommon.srping.factory;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: xjb-rpc
 * @description: 单例对象的工具类
 * @author: karl.xu
 * @create: 2020-10-14 13:48
 */
@Slf4j
public final class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    private SingletonFactory() {
    }

    public static <T> T getInstance(Class<T> c) {
        String key = c.toString();
        Object instance = null;
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = OBJECT_MAP.get(key);
                if (instance == null) {
                    try {
                        instance = c.getDeclaredConstructor().newInstance();
                        OBJECT_MAP.put(key, instance);
                    } catch (IllegalAccessException | InstantiationException e) {
                        log.error("IllegalAccessException | InstantiationException ,error={}", e);
                        throw new RuntimeException(e.getMessage(), e);
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        log.error("NoSuchMethodException | InvocationTargetException ,error={}", e);
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return c.cast(instance);
    }
}
