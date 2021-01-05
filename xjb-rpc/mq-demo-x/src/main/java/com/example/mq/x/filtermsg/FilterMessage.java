package com.example.mq.x.filtermsg;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2021-01-05 16:53
 */
public class FilterMessage {

    public static void main(String[] args) throws Exception {
        //发送消息时，你能通过putUserProperty来设置消息的属性
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.start();
        Message msg = new Message("TopicTest",
                "TAGA",
                ("Hello RocketMQ " + 0).getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
// 设置一些属性
        msg.putUserProperty("a", String.valueOf(0));
        SendResult sendResult = producer.send(msg);

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
// 只有订阅的消息有这个属性a, a >=0 and a <= 3
        consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3"));
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();


        producer.shutdown();


    }

}
