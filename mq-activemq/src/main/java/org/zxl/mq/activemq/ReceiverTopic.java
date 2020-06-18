package org.zxl.mq.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiverTopic {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");

        try {
            connection = connectionFactory.createConnection();
            connection.start();;
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
//            destination = session.createQueue("FirstQueue");
            destination = session.createTopic("Second");
            consumer = session.createConsumer(destination);
            while (true){
                TextMessage message = (TextMessage) consumer.receive(100000);
                if(null != message){
                    System.out.println("收到消息："+message.getText());
                }else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
