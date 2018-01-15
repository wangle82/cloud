package com.zhj.cloud.stream.kafka.raw;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zhanghongjun on 2017/12/7.
 *
 * 生产者的配置参考 config/producer.properties
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 初始化配置
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        // 创建 Kafka Producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties);
        // 创建 Kakfa 消息  = ProducerRecord
        String topic = "testzhj";
        Integer partition = 0;  // 消息发给哪个分区
        Long timestamp = System.currentTimeMillis();
        String key = "message-key";
        String value = "messagetest";
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(topic, partition, timestamp, key, value);
        // 发放 Kakfa 消息
        Future<RecordMetadata> metadataFuture =  kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println("发送成功");
            }
        });

    }

}
