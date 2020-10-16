package com.xjb.rpc.rpccommon.srping.init;

import com.xjb.rpc.rpccommon.annons.RpcScan;
import com.xjb.rpc.rpccommon.annons.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 初始化的时候加载需要扫描的RPC服务的数量
 *
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu ImportBeanDefinitionRegistrar, ResourceLoaderAware , BeanFactoryAware
 * @create: 2020-10-14 11:16
 */
@Slf4j
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware {

    private static final String SPRING_BEAN_BASE_PACKAGE = "com.xjb.rpc";
    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";
    private ResourceLoader resourceLoader;

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        //或者 RpcScan注解的属性
        AnnotationAttributes rpcScanAnnotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RpcScan.class.getName()));
        String[] rpcScanBasePackages = new String[0];
        if (rpcScanAnnotationAttributes != null) {
            // get the value of the basePackage property
            rpcScanBasePackages = rpcScanAnnotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
        }
        if (rpcScanBasePackages.length == 0) {
            rpcScanBasePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }
        // Scan the RpcService annotation
        CustomScanner rpcServiceScanner = new CustomScanner(registry, RpcService.class);
        // Scan the Component annotation
        CustomScanner springBeanScanner = new CustomScanner(registry, Component.class);
        if (resourceLoader != null) {
            rpcServiceScanner.setResourceLoader(resourceLoader);
            springBeanScanner.setResourceLoader(resourceLoader);
        }

        int springBeanAmount = springBeanScanner.scan(SPRING_BEAN_BASE_PACKAGE);
        log.info("springBeanScanner扫描的数量 [{}]", springBeanAmount);
        int rpcServiceCount = rpcServiceScanner.scan(rpcScanBasePackages);
        log.info("rpcServiceScanner扫描的数量 [{}]", rpcServiceCount);
    }

//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
//        //或者 RpcScan注解的属性
//        AnnotationAttributes rpcScanAnnotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RpcScan.class.getName()));
//        String[] rpcScanBasePackages = new String[0];
//        if (rpcScanAnnotationAttributes != null) {
//            // get the value of the basePackage property
//            rpcScanBasePackages = rpcScanAnnotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
//        }
//        if (rpcScanBasePackages.length == 0) {
//            rpcScanBasePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
//        }
//        // Scan the RpcService annotation
//        CustomScanner rpcServiceScanner = new CustomScanner(registry, RpcService.class);
//        // Scan the Component annotation
//        CustomScanner springBeanScanner = new CustomScanner(registry, Component.class);
//        if (resourceLoader != null) {
//            rpcServiceScanner.setResourceLoader(resourceLoader);
//            springBeanScanner.setResourceLoader(resourceLoader);
//        }
//        int springBeanAmount = springBeanScanner.scan(SPRING_BEAN_BASE_PACKAGE);
//        log.info("springBeanScanner扫描的数量 [{}]", springBeanAmount);
//        int rpcServiceCount = rpcServiceScanner.scan(rpcScanBasePackages);
//        log.info("rpcServiceScanner扫描的数量 [{}]", rpcServiceCount);
//    }


}

