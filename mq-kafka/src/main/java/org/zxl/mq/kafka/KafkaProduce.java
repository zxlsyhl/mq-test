package org.zxl.mq.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class KafkaProduce {
    public static void main(String[] args) {
        Properties prop = new Properties();
        //1.配置kafka节点地址
        prop.put("bootstrap.servers","192.168.1.105:9092");
        //2.发送消息是否应答
        prop.put("acks","all");
        //3.配置发送消息失败重试
        prop.put("retries","0");
        //4.配置批量处理消息大小
        prop.put("batch.size" ,"10241");
        //5.配置批量处理数据延迟
        prop.put("linger.ms","5");
        //6.配置内存缓冲大小
        prop.put("buffer.memory","1234321");

        //prop.put("retries","10");



        //7.信息发送前必须序列化
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //实例化
        KafkaProducer<String,String> prodecer = new KafkaProducer<String,String>(prop);
        for (int i = 0; i <99; i++){
            prodecer.send(new ProducerRecord<String, String>("nginxlog", "hah" + i), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (metadata!=null) {
                        System.out.println(metadata.topic() + "..." + metadata.offset() + "..." + metadata.partition());
                    }
                }
            });
        }

        prodecer.close();
    }
}
