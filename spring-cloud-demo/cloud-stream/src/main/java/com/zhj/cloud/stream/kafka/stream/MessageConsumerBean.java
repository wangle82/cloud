package com.zhj.cloud.stream.kafka.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by zhanghongjun on 2017/12/11.
 */
@Component
@EnableBinding({Sink.class})
public class MessageConsumerBean {
    @Autowired
    @Qualifier(Sink.INPUT) // Bean 名称
    private SubscribableChannel subscribableChannel;

    @Autowired
    private Sink sink;

    // 当字段注入完成后的回调
    @PostConstruct
    public void init() {
        // 实现异步回调
        subscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {

                System.out.println(">>>>>>>subscribe : " + message.getPayload());

            }
        });
    }

    //通过@ServiceActivator
    @ServiceActivator(inputChannel = Sink.INPUT)
    public void onMessage(Object message) {
        System.out.println(">>>>>>>@ServiceActivator : " + message);
    }

    @StreamListener(Sink.INPUT)
    public void onMessage(String message){
        System.out.println(">>>>>>>@StreamListener : " + message);
    }

}
