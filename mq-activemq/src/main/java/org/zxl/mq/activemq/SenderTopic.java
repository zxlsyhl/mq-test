package org.zxl.mq.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SenderTopic {
    public static void main(String[] args) {
        //连接工厂，JMS用于创建连接
        ConnectionFactory connectionFactory;
        //JMS provider连接
        Connection connection = null;
        //一个发送或接受消息的会话
        Session session ;
        //消息的目的地
        Destination destination;
        //消息发送者
        MessageProducer producer;
        //构造ConnectionFactory实例对象，此处采用ActiveMq实现的
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        try{
            //设计模式 todo
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            //获取操作连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("FirstQueue");

            Destination  destination1 = session.createTopic("Second");
            MessageProducer producer1 = session.createProducer(destination1);
            TextMessage message1 = session.createTextMessage("我想发送给多个人的消息");
            producer1.send(message1);

//            //实例化消息生产者
//            producer = session.createProducer(destination);
//            //创建需要发布的消息
//            TextMessage  message = session.createTextMessage("ActiveMq 发送的消息");
//            //设置不持久化
//            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//            //发送消息
//            producer.send(message);
            session.commit();
            System.out.println("发送成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
