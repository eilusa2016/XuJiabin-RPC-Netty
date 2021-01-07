package com.example.mq.x.openmessaging;

import io.openmessaging.*;

import java.nio.charset.Charset;

import io.openmessaging.producer.Producer;
import io.openmessaging.producer.SendResult;

import java.util.concurrent.CountDownLatch;


/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2021-01-05 19:05
 */
public class SimpleOpenProducer {
    public static void main(String[] args) {


        final MessagingAccessPoint messagingAccessPoint =
                OMS.getMessagingAccessPoint("oms:rocketmq://localhost:9876/default:default");
        final Producer producer = messagingAccessPoint.createProducer();
        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");
        producer.startup();
        System.out.printf("Producer startup OK%n");
        {
            Message message = producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8")));
            SendResult sendResult = producer.send(message);
            //final Void aVoid = result.get(3000L);
            System.out.printf("Send async message OK, msgId: %s%n", sendResult.messageId());
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        {
            final Future<SendResult> result = producer.sendAsync(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
            result.addListener(new FutureListener<SendResult>() {
                @Override
                public void operationComplete(Future<SendResult> future) {
                    if (future.getThrowable() != null) {
                        System.out.printf("Send async message Failed, error: %s%n", future.getThrowable().getMessage());
                    } else {
                        System.out.printf("Send async message OK, msgId: %s%n", future.get().messageId());
                    }
                    countDownLatch.countDown();
                }
            });
        }
        {
            producer.sendOneway(producer.createBytesMessage("OMS_HELLO_TOPIC", "OMS_HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
            System.out.printf("Send oneway message OK%n");
        }
        try {
            countDownLatch.await();
            Thread.sleep(500); // 等一些时间来发送消息
        } catch (InterruptedException ignore) {
        }
        producer.shutdown();
    }
}
