package com.xjb.rpc.rpccommon.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.xjb.rpc.rpccommon.exception.SerializeException;
import com.xjb.rpc.rpccommon.rpc.dto.RpcRequest;
import com.xjb.rpc.rpccommon.rpc.dto.RpcResponse;
import com.xjb.rpc.rpccommon.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-14 16:26
 */
@Slf4j
public class KryoSerializer implements Serializer {
    /**
     * Kryo不是线程安全的
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    /**
     * Kryo不是线程安全的  这种是自带的线程池来实现
     * @return
     */
    public KryoPool newKryoPool() {
        return new KryoPool.Builder(() -> {
            final Kryo kryo = new Kryo();
            kryo.register(RpcResponse.class);
            kryo.register(RpcRequest.class);
            return kryo;
        }).softReferences().build();
    }


    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException("Serialization failed");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializeException("Deserialization failed");
        }
    }
}
