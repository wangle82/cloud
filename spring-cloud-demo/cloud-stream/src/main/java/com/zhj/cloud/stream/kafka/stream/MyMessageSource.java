package com.zhj.cloud.stream.kafka.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by zhanghongjun on 2017/12/11.
 * 自定义输出管道  指定管道名
 */
public interface MyMessageSource {
    /**
     * 消息来源的管道名称：""
     */
    String OUTPUT = "mychannel";

    @Output(OUTPUT)
    MessageChannel mychannel();

}
