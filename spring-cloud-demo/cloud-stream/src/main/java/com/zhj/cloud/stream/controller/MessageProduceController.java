package com.zhj.cloud.stream.controller;

import com.zhj.cloud.stream.kafka.stream.MessageProducerBean;
import com.zhj.cloud.stream.kafka.template.KafkaTemplateStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhanghongjun on 2017/12/11.
 */
@RestController
public class MessageProduceController {
    @Autowired
    private KafkaTemplateStyle kafkaTemplateStyle;
    @GetMapping("/message/send")
    public Boolean sendMessage(@RequestParam String message) {
        kafkaTemplateStyle.sendMessage( message);
        return true;
    }
    @Autowired
    private MessageProducerBean messageProducerBean;
    @GetMapping("/stream/message/send")
    public void sendDefaultStreamMessage(@RequestParam String message) {
        messageProducerBean.sendDefaultTopic(message);
    }
    @GetMapping("/stream/message/send/testzhj")
    public void sendMyStreamMessage(@RequestParam String message) {
        messageProducerBean.sendMyTopic(message);
    }
}
