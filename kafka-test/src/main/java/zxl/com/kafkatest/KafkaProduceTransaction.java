package zxl.com.kafkatest;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaException;

import java.util.Properties;

/**
 * kafka生产者事务，避免生产者重复生产
 */
public class KafkaProduceTransaction {
    public static void main(String[] args) {

        // Producer 配置信息，应该配置在属性文件中
        Properties props = new Properties();
        //指定要连接的 broker，不需要列出所有的 broker，但建议至少列出2个，以防某个 broker 挂了
        props.put("bootstrap.servers", "192.168.1.105:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("transactional.id", "test.transactional.id"); // 需要设置 transactional.id

        // 创建 Producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // 初始化事务
        producer.initTransactions();

        try {
            // 开启事务
            producer.beginTransaction();

            // 发送消息
            producer.send(new ProducerRecord<String, String>("nginxlog","222", "message 5"), new Callback() {

                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(exception != null) {
                        System.out.println("send message5 failed with " + exception.getMessage());
                    } else {
                        String a = null;
                        System.out.println(a.toString());
                        // offset 是消息在 partition 中的编号，可以根据 offset 检索消息
                        System.out.println("message5 sent to " + metadata.topic() + ", partition " + metadata.partition() + ", offset " + metadata.offset());

                    }

                }

            });

//            String a = null;
//            System.out.println(a.toString());
            // 提交事务
            producer.commitTransaction();

        } catch(KafkaException e) {
            // 终止事务
            producer.abortTransaction();
        }
        catch (Exception e){
            e.printStackTrace();
            producer.abortTransaction();
        }finally {
            producer.close();
        }

    }
}
