package com.xjb.rpc.client.controller;

import com.xjb.rpc.api.pojo.Hello;
import com.xjb.rpc.api.services.HelloService;
import com.xjb.rpc.rpccommon.annons.RpcReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-16 11:30
 */
@RestController
@RequestMapping("rpc")
public class RpcTestController {

    @RpcReference(group = "test1",version = "version1")
    private HelloService helloService;

    @PostMapping("/test")
    public String test() throws InterruptedException {
        String hello = helloService.hello(new Hello("111", "222"));
        return hello;
        //如需使用 assert 断言，需要在 VM options 添加参数：-ea
//        assert "Hello description is 222".equals(hello);
//        System.out.println(hello);
//        Thread.sleep(12000);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(helloService.hello(new Hello("111", "222")));
//        }
    }



}
