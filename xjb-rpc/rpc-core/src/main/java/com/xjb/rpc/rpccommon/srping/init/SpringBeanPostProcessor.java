package com.xjb.rpc.rpccommon.srping.init;

import com.xjb.rpc.rpccommon.annons.RpcReference;
import com.xjb.rpc.rpccommon.annons.RpcService;
import com.xjb.rpc.rpccommon.entity.RpcServiceProperties;
import com.xjb.rpc.rpccommon.extension.ExtensionLoader;
import com.xjb.rpc.rpccommon.provider.ServiceProviderImpl;
import com.xjb.rpc.rpccommon.proxy.RpcClientProxy;
import com.xjb.rpc.rpccommon.rpc.ClientTransport;
import com.xjb.rpc.rpccommon.provider.ServiceProvider;
import com.xjb.rpc.rpccommon.srping.factory.SingletonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @program: xjb-rpc
 * @description: 初花bean完成前后操作的类
 * @author: karl.xu
 * @create: 2020-10-14 11:48
 */
@Slf4j
@Component
public class SpringBeanPostProcessor  implements BeanPostProcessor {

    private final ServiceProvider serviceProvider;
    private final ClientTransport rpcClient;

    public SpringBeanPostProcessor(){
        this.serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
        this.rpcClient = ExtensionLoader.getExtensionLoader(ClientTransport.class).getExtension("nettyClientTransport");
    }

    /**
     * 加载完成前---加载RPC的服务实现类
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            log.info("[{}] 的注解是  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            // get RpcService annotation
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            // build RpcServiceProperties
            RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                    .group(rpcService.group()).version(rpcService.version()).build();
            serviceProvider.publishService(bean, rpcServiceProperties);
        }
        return bean;
    }

    /**
     * bean加载完成后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                        .group(rpcReference.group()).version(rpcReference.version()).build();
                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceProperties);
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return bean;
    }


}
