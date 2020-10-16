package com.xjb.rpc.rpccommon.srping.init;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 从指定包内扫描所有组件bean定义的
 * 接收一组包的名称，然后在这些包内扫描所有的类，
 *   查找其中符合条件的bean组件定义并将这些bean组件定义注册到容器。
 *   这些bean定义注册到容器时具体使用的类为ScannedGenericBeanDefinition,这是Spring bean定义模型接口BeanDefinition的一个具体实现类，针对扫描得到的bean定义。
 *
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu ClassPathBeanDefinitionScanner
 * @create: 2020-10-14 11:45
 */
public class CustomScanner extends ClassPathBeanDefinitionScanner {
    /**
     *  查找其中符合条件的bean组件定义并将这些bea添加扫描过滤器。
     * @param registry
     * @param annoType
     */
    public CustomScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annoType) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annoType));
    }

    /**
     * 接收一组包的名称，然后在这些包内扫描所有的类，
     * @param basePackages
     * @return
     */
    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
       return super.doScan(basePackages);
    }
}
