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
//import com.aliyun.openservices.ons.api.Producer;
//import com.aliyun.openservices.ons.api.PropertyKeyConst;
//
//public class LMQUtils {
//    /**
//     * 设置阿里云的AccessKey，用于鉴权
//     */
//	private static final String acessKey ="LTAIqmAgj5CwrchH";
//    /**
//     * 设置阿里云的SecretKey，用于鉴权
//     */
//    private static final String secretKey ="j0z6j9d1uei04OqxqMkcFa0qsqaIhI";
//    /**
//     * 收消息使用的一级Topic，需要先在MQ控制台里申请
//     */
//    private static final String topic ="xieTopic";
//	/**
//	 * ConsumerID ,需要先在MQ控制台里申请
//	 */
//	private static final String consumerID = "CID_xieConsumer";
//	/**
//	 * ProducerID，需要先在MQ控制台里申请
//	 */
//	private static final String producerId = "PID_xieProducer";
//
////	private static Producer producer;
//	private static Consumer consumer;
//	
//	static {
//		Properties properties2 = new Properties();
//		properties2.put(PropertyKeyConst.ConsumerId, consumerID);
//		properties2.put(PropertyKeyConst.AccessKey, acessKey);
//		properties2.put(PropertyKeyConst.SecretKey, secretKey);
//		consumer = ONSFactory.createConsumer(properties2);
//		consumer.start();
//	}
//
//	private static Producer getProducer() {
//		Properties properties1 = new Properties();
//		properties1.put(PropertyKeyConst.ProducerId, producerId);
//		properties1.put(PropertyKeyConst.AccessKey, acessKey);
//		properties1.put(PropertyKeyConst.SecretKey, secretKey);
//		Producer producer = ONSFactory.createProducer(properties1);
//		producer.start();
//		return producer;
//	}
//
//	public static void sendMsg(byte[] body) {
//		Producer producer = getProducer();
//		try {
//			final Message msg = new Message(topic, "MQ2MQTT", body);
//			producer.send(msg);
//			System.out.println("发送成功...........");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			producer.shutdown();
//			producer = null;
//		}
//	}
//
//	public static void receiveMsg(ILMQService service) {
//		consumer.subscribe(topic, "*", new MessageListener() {
//			public Action consume(Message message, ConsumeContext consumeContext) {
//				service.doTask(message);
//				return Action.CommitMessage;
//			}
//		});
//		System.out.println("启动成功...等待接受消息");
//	}
//
//}
