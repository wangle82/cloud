package com.zhj.consumer.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.netflix.hystrix.HystrixHealthIndicator;
import org.springframework.cloud.netflix.hystrix.HystrixStreamEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by zhanghongjun on 2017/11/20.
 * Hystrix 配置信息wiki：https://github.com/Netflix/Hystrix/wiki/Configuration
 *
 *1   /health端点中会增加hystrix节点指标
 * @see  HystrixHealthIndicator
 *
 * 2 同时增加 /hystrix.stream 端点，会输出所有短路熔断的信息
 * @see HystrixStreamEndpoint
 *
 * 3 /hystrix.stream 端点信息可读性性差，
 *      所以引入hystrix-dashboard依赖，同时激活@EnableHystrixDashboard
 *          通过/hystrix  实现对短路的图形化统计
 *
 */
@RestController
public class HystrixRestDemoController {
    private final static Random random = new Random();

    // 1 注解方式
    @GetMapping("hello")
    @HystrixCommand(
            fallbackMethod = "errorContent", //配置失败方法

            commandProperties = {
                    // 配置超时属性
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }

    )
    public String hello() throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);

        System.out.println("helloWorld() costs " + value + " ms.");

        Thread.sleep(value);

        return "Hello,World";
    }

    public String errorContent() {
        return "Fault";
    }


    // 2编程方式
    @GetMapping("hello2")
    public String helloWorld2() {
        return new HelloWorldCommand().execute();
    }


    private class HelloWorldCommand extends com.netflix.hystrix.HystrixCommand<String> {

        protected HelloWorldCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"),
                    3000);


        }

        @Override
        protected String run() throws Exception {
            // 如果随机时间 大于 100 ，那么触发容错
            int value = random.nextInt(200)+1000;

            System.out.println("helloWorld() costs " + value + " ms.");

            Thread.sleep(value);

            return "Hello,World";
        }

        //容错执行
        @Override
        protected String getFallback() {
            return HystrixRestDemoController.this.errorContent();
        }

    }
}