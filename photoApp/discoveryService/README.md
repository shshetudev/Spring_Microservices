## Issues:

* Wrong library added:
  * __Error format:__
      - `java.lang.NullPointerException: null
        at com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.shutdown(PeerAwareInstanceRegistryImpl.java:176) ~[eureka-core-1.10.17.jar:1.10.17]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]`
      - `org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'org.springframework.cloud.netflix.eureka.server.EurekaServerInitializerConfiguration': Unsatisfied dependency expressed through field 'eurekaServerBootstrap'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'eurekaServerBootstrap' defined in class path resource [org/springframework/cloud/netflix/eureka/server/EurekaServerAutoConfiguration.class]: Unsatisfied dependency expressed through method 'eurekaServerBootstrap' parameter 1; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'eurekaServerContext': Invocation of init method failed; nested exception is java.lang.IllegalStateException: java.lang.RuntimeException: Cannot Create new Replica Node :JerseyReplicationClient: http://localhost:8010/eureka/apps/:
        at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:659) ~[spring-beans-5.3.16.jar:5.3.16]
        `
  * **Solution:** Don't add both client and server library that is:
    - `implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'`
    - `implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'`
    - Remove one.