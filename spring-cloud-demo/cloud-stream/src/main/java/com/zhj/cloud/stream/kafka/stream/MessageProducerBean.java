package com.zhj.cloud.stream.kafka.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static javafx.scene.input.KeyCode.M;

/**
 * Created by zhanghongjun on 2017/12/11.
 */
@Component
@EnableBinding({Source.class,MyMessageSource.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(Source.OUTPUT) // Bean 名称
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    @Autowired
    private MyMessageSource mssageSource;

    @Autowired
    @Qualifier(MyMessageSource.OUTPUT) // Bean 名称
    private MessageChannel myChannel;

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendDefaultTopic(String message){
        // 通过消息管道发送消息
//        messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }

    /**
     * @param message 消息内容
     */
    public void sendMyTopic(String message){
        // 通过消息管道发送消息
        myChannel.send(MessageBuilder.withPayload(message).build());
    }

}

