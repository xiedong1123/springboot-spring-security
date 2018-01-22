package com.ccl.base.mq;
//package com.ccl.common.mq;
//
//import java.util.Properties;
//
//import com.aliyun.openservices.ons.api.Action;
//import com.aliyun.openservices.ons.api.ConsumeContext;
//import com.aliyun.openservices.ons.api.Consumer;
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.MessageListener;
//import com.aliyun.openservices.ons.api.ONSFactory;
//import com.aliyun.openservices.ons.api.PropertyKeyConst;
//
//public class ONSRecvMsg {
//    public static void main(String[] args) throws InterruptedException {
//        /**
//         * 设置阿里云的AccessKey，用于鉴权
//         */
//        final String acessKey ="LTAIqmAgj5CwrchH";
//        /**
//         * 设置阿里云的SecretKey，用于鉴权
//         */
//        final String secretKey ="j0z6j9d1uei04OqxqMkcFa0qsqaIhI";
//        /**
//         * 收消息使用的一级Topic，需要先在MQ控制台里申请
//         */
//        final String topic ="xieTopic";
//        /**
//         * ConsumerID ,需要先在MQ控制台里申请
//         */
//        final String consumerID ="CID_xieConsumer";
//        Properties properties =new Properties();
//        properties.put(PropertyKeyConst.ConsumerId, consumerID);
//        properties.put(PropertyKeyConst.AccessKey, acessKey);
//        properties.put(PropertyKeyConst.SecretKey, secretKey);
//        Consumer consumer =ONSFactory.createConsumer(properties);
//        /**
//         * 此处MQ客户端只需要订阅MQTT的一级Topic即可
//         */
//        consumer.subscribe(topic, "*", new MessageListener() {
//            public Action consume(Message message, ConsumeContext consumeContext) {
//                System.out.println("recv msg:"+message);
//                byte[] body = message.getBody();
//                System.out.println("recv msg:"+new String(body));
//                return Action.CommitMessage;
//            }
//        });
//        consumer.start();
//        System.out.println("[Case Normal Consumer Init]   Ok");
//        Thread.sleep(Integer.MAX_VALUE);
//        consumer.shutdown();
//        System.exit(0);
//    }
//}