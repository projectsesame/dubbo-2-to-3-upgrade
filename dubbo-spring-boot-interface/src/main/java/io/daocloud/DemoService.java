package io.daocloud;

import org.apache.dubbo.config.annotation.ProvidedBy;

import java.util.HashMap;

@ProvidedBy(name = {"dubbo-springboot-demo-provider"})
public interface DemoService {
    HashMap<String, String> sayHello(String name);
}
