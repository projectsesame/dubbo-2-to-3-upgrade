package io.daocloud.services;

import io.daocloud.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public HashMap<String, String> sayHello(String name) {
        HashMap<String, String> res = new HashMap<>();
        res.put("name", name);
        res.put("rpc.url", RpcContext.getServerAttachment().getUrl().toString());
        for (Map.Entry<String, Object> entry : RpcContext.getServerAttachment().getObjectAttachments().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // 处理每个附件的键值对
            res.put("headers."+key, value.toString());
        }
        try {
            res.put("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ignored) {}
        return res;
    }
}
