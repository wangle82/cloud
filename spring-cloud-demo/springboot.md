## 基础

### 1 打包

无论是jar或war包，都会在打包后执行spring-boot-maven-plugin的repackage goal

本质上就是向META-INF/MANIFEST.MF写入了不同的清单元素：

```properties
# 对于jar
Main-Class: org.springframework.boot.loader.JarLauncher
Start-Class: com.iqiyi.readingpartner.ReadingPartnerApplication
# 对于war: (当然如果项目没有提供web.xml,需要对war插件进行 <failOnMissingWebXml>false</failOnMissingWebXml>配置 方可打成war包)
Main-Class: org.springframework.boot.loader.WarLauncher
Start-Class: com.iqiyi.readingpartner.ReadingPartnerApplication
```

 ### 2 条件注解


ConfigurationClassParser#processConfigurationClass(ConfigurationClass configClass)
     会根据配置类的注解来决定是否跳过该配置bean
     大概流程是：
        A 首先获取配置类上A上的AnnotatedTypeMetadata(保存了所有注解信息),获取所有的Conditional注解(应该会递归查找A注解上的注解 比如A上有注解@ConditionalOnClass 会进一步查找ConditionalOnClass注解上的@Conditional)
                其实主要是为了获取所有的Conditional注解的value配置，即所有的Condition实现(这些实现可以指定顺序 通过@Order指定)
        B  然后按指定的顺序循环遍历所有  Condition#matches(ConditionContext context, AnnotatedTypeMetadata metadata)
            比如OnClassCondition的实现(见getMatchOutcome方法) 则是根据getCandidates(AnnotatedTypeMetadata metadata,Class<?> annotationType)方法 分别获取ConditionalOnClass 和 ConditionalOnMissingClass注解做判断

> 禁用指定的自动装配@SpringBootApplication(exclude ={HibernateJpaAutoConfiguration.class,DataSourceAutoConfiguration.class} )



### 3端点

```reStructuredText
AbstractEndpoint        
        ChannelsEndpoint 
        RequestMappingEndpoint 
        RestartEndpoint 
        ShutdownEndpoint 
        AutoConfigurationReportEndpoint
        LoggersEndpoint 
        MetricsEndpoint  /metrics  http gc 线程 内存
        ArchaiusEndpoint 
        BeansEndpoint 
        ConfigurationPropertiesReportEndpoint 
        EnvironmentEndpoint  /env   yml配置 系统属性 环境变量
        InfoEndpoint (org.springframework.boot.actuate.endpoint)
        HealthEndpoint  /health 各种中间件的简单心跳监控  db redis mongo jms 磁盘
        RoutesEndpoint (org.springframework.cloud.netflix.zuul)
        FeaturesEndpoint (org.springframework.cloud.client.actuator)
        ResumeEndpoint in RestartEndpoint 
        PauseEndpoint in RestartEndpoint 
        DumpEndpoint 
        TraceEndpoint 
        RefreshEndpoint 
```

#### 3.1  /env

> Spring抽象了一个Environment来表示Spring应用程序环境配置，它整合了各种各样的外部环境，并且提供统一访问的方法

```java
/**
两个实现
1  StandardEnvironment：标准环境，普通Java应用时使用，会自动注册System.getProperties() 和 System.getenv()到环境
2  StandardServletEnvironment：标准Servlet环境，其继承了StandardEnvironment，Web应用时使用，除了StandardEnvironment外，会自动注册ServletConfig（DispatcherServlet）、ServletContext及有选择性的注册JNDI实例到环境
*/
public interface Environment extends PropertyResolver {  
    String[] getActiveProfiles();    
    String[] getDefaultProfiles();   
    boolean acceptsProfiles(String... profiles);  

}
```

> `PropertyResolver`实现类具有解析PropertySource、根据PropertySource转换文本中的占位符的能力

```reStructuredText
PropertySource
	EnvironmentPropertySource (org.springframework.cloud.config.server.support)
	StubPropertySource in PropertySource (org.springframework.core.env)
	EnumerablePropertySource (org.springframework.core.env)
		EnumerableCompositePropertySource (org.springframework.boot.env)
		CompositePropertySource //内部可存多个PropertySource
		CommandLinePropertySource (org.springframework.core.env)
		MapPropertySource (org.springframework.core.env)
			PropertiesPropertySource (org.springframework.core.env)
				ResourcePropertySource
			SystemEnvironmentPropertySource (org.springframework.core.env)
		ConfigurationPropertySources in ConfigFileApplicationListener 
		ServletConfigPropertySource (org.springframework.web.context.support)
		ServletContextPropertySource (org.springframework.web.context.support)
		AnnotationsPropertySource 
	JndiPropertySource (org.springframework.jndi)
	RandomValuePropertySource (org.springframework.boot.context.config)

PropertySources extends Iterable<PropertySource<?>> //包含多个PropertySource
	MutablePropertySources (org.springframework.core.env)
	FlatPropertySources in ConfigurationPropertiesBindingPostProcessor 

```

> [`Spring Boot 优先级顺序`](https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/#boot-features-external-config)

将指定配置文件声明到env中

```java
@Configuration  
@PropertySource(value = "classpath:resources.properties", ignoreResourceNotFound = false)  
public class AppConfig {  
}  
```

通过`@Profile("prod")`声明不同环境下的bean,最后通过`@ActiveProfiles("dev")`激活环境中特定的bean

> env中自定义配置

```java
	@Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class MyPropertySourceLocator implements PropertySourceLocator {

        @Override
        public PropertySource<?> locate(Environment environment) {
            Map<String, Object> source = new HashMap<>();
            source.put("server.port","9090");
            MapPropertySource propertySource =
                    new MapPropertySource("my-property-source", source);
            return propertySource;
        }
    }

```

 /META-INF/spring.factories:

```properties
org.springframework.cloud.bootstrap.BootstrapConfiguration=\   com.zhj.MyPropertySourceLocator

```

#### 3.2 /health

实现类：`HealthEndpoint`

健康指示器：`HealthIndicator`，

`HealthEndpoint`：`HealthIndicator` ，一对多

> 自定义

```java
@Component
public class MyHealthIndicator extends AbstractHealthIndicator {

       @Override
       protected void doHealthCheck(Health.Builder builder)
               throws Exception {
           builder.up().withDetail("MyHealthIndicator","Day Day Up");
       }
   }

```



## JPA

1 

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
 </dependency>
```



```java
@EnableTransactionManagement(proxyTargetClass=false)
```


```java
// 1 手动注入，使用原生jpa
//@PersistenceContext
 //private EntityManager entityManager;

// 2 使用spring-data-jpa
@Repository
public interface UserJpaRepository extends PagingAndSortingRepository<User,Long> {
}
```

2 配置 

根据DataSourceAutoConfiguration ->@EnableConfigurationProperties(DataSourceProperties.class)

进行DataSourceProperties的配置

```yaml
spring:
  datasource:
    driver-class-name:
    url:
    username:
    password:
  jpa:
    show-sql:
    generate-ddl: 
```

