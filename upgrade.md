# 切换到k8s注册中心

由于是基于k8s的服务发现，所以本地启动不能调用成功。只能将两个项目部署在k8s环境中验证
## 更换依赖

添加dubbo-kubernetes依赖,这里使用的是3.2.9版本

```xml

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-kubernetes</artifactId>
    <version>3.2.9</version>
</dependency>
```

## 添加提供方注解

在服务实现上添加@DubboService注解并注明版本[DemoServiceImpl.java](dubbo-spring-boot-provider%2Fsrc%2Fmain%2Fjava%2Fio%2Fdaocloud%2Fservices%2FDemoServiceImpl.java)

在服务接口上添加@ProvidedBy注解并注明提供方服务，多个可用都逗号隔开[DemoService.java](dubbo-spring-boot-interface%2Fsrc%2Fmain%2Fjava%2Fio%2Fdaocloud%2FDemoService.java)

## 添加消费方注解

使用@DubboReference注入并注明版本[DemoController.java](dubbo-spring-boot-consumer%2Fsrc%2Fmain%2Fjava%2Fio%2Fdaocloud%2Fcontroller%2FDemoController.java)

## 修改配置文件

设置 Dubbo 项目使用 Kubernetes 作为注册中心，这里通过 DEFAULT_MASTER_HOST指定使用默认 API-SERVER 集群地址

```yaml
dubbo:
  application:
    name: ${APP_NAME:dubbo-springboot-demo-consumer}
    qos-enable: true
    qos-accept-foreign-ip: true
  registry:
    address: ${REG_ADDRESS:kubernetes://DEFAULT_MASTER_HOST?registry-type=service&duplicate=false&namespace=dubbo-demo}
```

## 打包部署

通过makefile进行打包并推送

使用[k8s-build](k8s-build)文件下的yaml进行部署

***注:k8s中的provider service name 与项目中的 dubbo 应用名是一样的***

## 验证

访问consumer的8080端口验证dubbo调用是否成功 http://consumer:8080/hello

## 可供参考资料

[dubbo官方文档](https://cn.dubbo.apache.org/zh/overview/tasks/kubernetes/deploy-on-k8s/)  
[dubbo官方实例](https://github.com/apache/dubbo-samples/tree/master/3-extensions/registry/dubbo-samples-kubernetes)  
[为什么需要使用@ProviderBy配置提供方服务名](https://github.com/apache/dubbo/issues/10374)  
[不想配置@ProviderBy的情况下如何使用元数据中心](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/metadata-center/overview/)  
