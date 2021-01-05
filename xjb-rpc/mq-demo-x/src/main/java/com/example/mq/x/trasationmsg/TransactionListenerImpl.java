package com.example.mq.x.trasationmsg;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: xjb-rpc
 * @description: 实现事务的监听接口
 * 发送半消息成功时，我们使用 executeLocalTransaction 方法来执行本地事务。
 * 它返回前一节中提到的三个事务状态之一。
 * checkLocalTransaction 方法用于检查本地事务状态，并回应消息队列的检查请求。
 * 它也是返回前提到的三个事务状态之一
 * @author: karl.xu
 * @create: 2021-01-05 16:58
 */
public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//        int value = transactionIndex.getAndIncrement();
//        int status = value % 3;
//        localTrans.put(msg.getTransactionId(), status);
//        return LocalTransactionState.UNKNOW;

        String msgBody;
        try {
            //执行本地业务的时候，再插入一条数据到事务表中，供checkLocalTransaction进行check使用，避免doBusinessCommit业务成功，但是未返回Commit
            msgBody = new String(msg.getBody(), "utf-8");
            doBusinessCommit(msg.getKeys(),msgBody);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;

        }

    }

    /**
     * 执行提交业务提交数据
     * @param messageKey
     * @param msgbody
     */
    public static void doBusinessCommit(String messageKey,String msgbody){
        System.out.println("do something in DataBase");
        System.out.println("insert 事务消息到本地消息表中，消息执行成功，messageKey为："+messageKey+"\t 消息体="+msgbody);
    }

    public static Boolean checkBusinessStatus(String messageKey){
        if(true){
            System.out.println("查询数据库 messageKey为"+messageKey+"的消息已经消费成功了，可以提交消息");
            return true;
        }else{
            System.out.println("查询数据库 messageKey为"+messageKey+"的消息不存在或者未消费成功了，可以回滚消息");
            return false;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//        Integer status = localTrans.get(msg.getTransactionId());
//        if (null != status) {
//            switch (status) {
//                case 0:
//                    return LocalTransactionState.UNKNOW;
//                case 1:
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                case 2:
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//            }
//        }
//        return LocalTransactionState.COMMIT_MESSAGE;
        Boolean result=checkBusinessStatus(msg.getKeys());
        if(result){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else{
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

    }
}
