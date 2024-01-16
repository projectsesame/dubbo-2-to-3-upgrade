package io.daocloud.controller;

import io.daocloud.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping("/hello")
    public HashMap<String, Object> sayHello(@RequestHeader MultiValueMap<String, String> headers, String name) {
        String laneName = System.getenv("LANE_NAME");
        if (laneName == null) {
            laneName = "lane";
        }
        String v = headers.getFirst(laneName);
        if (v!=null) {
            RpcContext.getClientAttachment().setAttachment(laneName, v);
        }
        HashMap<String, String> res = demoService.sayHello(name);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("provider", res);
        try {
            hm.put("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ignored) {}
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            hm.put("headers."+key, value.toString());
        }
        return hm;
    }
}
