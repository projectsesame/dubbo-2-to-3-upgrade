package io.daocloud.controller;

import io.daocloud.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @DubboReference(url = "tri://127.0.0.1:20880")
    private DemoService demoService;

    @RequestMapping("/hello")
    public String sayHello() {
        return demoService.sayHello("daocloud");
    }
}
