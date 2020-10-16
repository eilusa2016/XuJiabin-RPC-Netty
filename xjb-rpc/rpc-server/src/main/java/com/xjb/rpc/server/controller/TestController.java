package com.xjb.rpc.server.controller;

import com.xjb.rpc.api.pojo.Hello;
import com.xjb.rpc.server.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2020-10-14 17:01
 */
@RestController
public class TestController {

    @Resource
    private HelloServiceImpl helloService;

    @RequestMapping("index")
    public String  index(){
        helloService.hello(new Hello());
        return "1";
    }


}
