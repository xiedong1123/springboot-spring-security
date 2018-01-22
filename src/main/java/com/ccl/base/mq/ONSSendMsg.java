package com.ccl.base.mq;
//package com.ccl.common.mq;
//
//import java.util.Properties;
//
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.ONSFactory;
//import com.aliyun.openservices.ons.api.Producer;
//import com.aliyun.openservices.ons.api.PropertyKeyConst;
//import com.aliyun.openservices.ons.api.SendResult;
//
//public class ONSSendMsg {
//	public static void main(String[] args) throws InterruptedException {
//		/**
//		 * 设置阿里云的AccessKey，用于鉴权
//		 */
//		final String acessKey = "LTAIqmAgj5CwrchH";
//		/**
//		 * 设置阿里云的SecretKey，用于鉴权
//		 */
//		final String secretKey = "j0z6j9d1uei04OqxqMkcFa0qsqaIhI";
//		/**
//		 * 发消息使用的一级Topic，需要先在MQ控制台里申请
//		 */
//		final String topic = "xieTopic";
//		/**
//		 * ProducerID，需要先在MQ控制台里申请
//		 */
//		final String producerId = "PID_xieProducer";
//		Properties properties = new Properties();
//		properties.put(PropertyKeyConst.ProducerId, producerId);
//		properties.put(PropertyKeyConst.AccessKey, acessKey);
//		properties.put(PropertyKeyConst.SecretKey, secretKey);
//		Producer producer = ONSFactory.createProducer(properties);
//		producer.start();
//		byte[] body = new byte[1024];
//		body = "我拉窗外诶诶还海军1".getBytes();
//		final Message msg = new Message(topic, // MQ消息的Topic，需要事先申请
//				"MQ2MQTT", // MQ Tag，通过MQ向MQTT客户端发消息时，必须指定MQ2MQTT作为Tag，其他Tag或者不设都将导致MQTT客户端收不到消息
//				body);// 消息体，和MQTT的body对应
//		/**
//		 * 使用MQ客户端给MQTT设备发送P2P消息时，需要在MQ消息中设置mqttSecondTopic属性 设置的值是“/p2p/”+目标ClientID
//		 */
//		// String targetClientID="GID_MQTTTestTopic@@@DeviceID_0001";
//		// msg.putUserProperties("mqttSecondTopic", "/p2p/"+targetClientID);
//		// 发送消息，只要不抛异常就是成功。
//		@SuppressWarnings("unused")
//		SendResult sendResult = producer.send(msg);
//		// System.out.println(sendResult);
//		/**
//		 * 如果仅仅发送Pub/Sub消息，则只需要设置实际MQTT订阅的Topic即可，支持设置二级Topic
//		 */
//		// msg.putUserProperties("mqttSecondTopic", "/notice/");
//		// SendResult result =producer.send(msg);
//		producer.shutdown();
//		System.exit(0);
//	}
//}