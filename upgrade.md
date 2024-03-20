# 切换到xml+mesh

dubbo版本>=3.1.2

## 修改提供方xml
去除注册中心并且添加如下配置
```xml
<dubbo:protocol name="tri" port="20880" />
```
使用mesh方式的话协议必须为tri

## 修改消费方xml
去除注册中心并且将dubbo:reference修改为如下配置
```xml
    <dubbo:protocol name="tri"/>
    <dubbo:reference interface="io.daocloud.DemoService" id="demoService" provided-by="dubbo-springboot-demo-provider" provider-namespace="dubbo-demo" provider-port="20880"/>
```
provided-by(必填):  提供方服务名
provider-namespace(非必填): 提供方在k8s下的命名空间，默认为default
provider-port(非必填):  提供方方服务端口，默认为80

## 打包部署

通过makefile进行打包并推送

使用[k8s-build](k8s-build)文件下的yaml进行部署

***注:k8s中的provider service name 与项目中的 dubbo 应用名是一样的***

## 验证

访问consumer的8080端口验证dubbo调用是否成功 http://consumer:8080/hello

## 可供参考资料

[dubbo官方文档](https://cn.dubbo.apache.org/zh-cn/overview/tasks/mesh/dubbo-mesh/)  
[dubbo官方实例](https://github.com/apache/dubbo-samples/tree/master/3-extensions/registry/dubbo-samples-mesh-k8s)