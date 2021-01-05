package com.example.mq.x.batchmsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: xjb-rpc
 * @description:
 * @author: karl.xu
 * @create: 2021-01-05 15:51
 */
public class BatchProducer {

    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // 启动生产者
        producer.start();
        int totalMessagesToSend = 50;
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
//            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
//            message.setDelayTimeLevel(3);
            // 发送消息
            messages.add(message);
        }
        /**
         * 消息不超过4MB，则很容易使用批处理
         */
        producer.send(messages);

        /**
         * 复杂度只有当你发送大批量时才会增长，你可能不确定它是否超过了大小限制（4MB）。这时候你最好把你的消息列表分割一下
         */
//        //把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message> listItem = splitter.next();
                producer.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }
        }

        // 关闭生产者
        producer.shutdown();
    }

    private static  class ListSplitter implements Iterator<List<Message>> {
        private final int SIZE_LIMIT = 1024 * 1024 * 4;
        private final List<Message> messages;
        private int currIndex;

        public ListSplitter(List<Message> messages) {
            this.messages = messages;
        }

        @Override
        public boolean hasNext() {
            return currIndex < messages.size();
        }

        @Override
        public List<Message> next() {
            int startIndex = getStartIndex();
            int nextIndex = startIndex;
            int totalSize = 0;
            for (; nextIndex < messages.size(); nextIndex++) {
                Message message = messages.get(nextIndex);
                int tmpSize = calcMessageSize(message);
                if (tmpSize + totalSize > SIZE_LIMIT) {
                    break;
                } else {
                    totalSize += tmpSize;
                }
            }
            List<Message> subList = messages.subList(startIndex, nextIndex);
            currIndex = nextIndex;
            return subList;
        }

        private int getStartIndex() {
            Message currMessage = messages.get(currIndex);
            int tmpSize = calcMessageSize(currMessage);
            while (tmpSize > SIZE_LIMIT) {
                currIndex += 1;
                Message message = messages.get(currIndex);
                tmpSize = calcMessageSize(message);
            }
            return currIndex;
        }

        private int calcMessageSize(Message message) {
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize = tmpSize + 20; // 增加⽇日志的开销20字节
            return tmpSize;
        }
    }

}
