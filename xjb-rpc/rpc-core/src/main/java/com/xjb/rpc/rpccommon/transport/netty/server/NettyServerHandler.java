package com.xjb.rpc.rpccommon.transport.netty.server;

import com.xjb.rpc.rpccommon.enums.CompressTypeEnum;
import com.xjb.rpc.rpccommon.enums.RpcResponseCodeEnum;
import com.xjb.rpc.rpccommon.enums.SerializationTypeEnum;
import com.xjb.rpc.rpccommon.rpc.constants.RpcConstants;
import com.xjb.rpc.rpccommon.rpc.dto.RpcMessage;
import com.xjb.rpc.rpccommon.rpc.dto.RpcRequest;
import com.xjb.rpc.rpccommon.rpc.dto.RpcResponse;
import com.xjb.rpc.rpccommon.rpc.handler.RpcRequestHandler;
import com.xjb.rpc.rpccommon.srping.factory.SingletonFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Customize the ChannelHandler of the server to process the data sent by the client.
 * <p>
 * 如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @author shuang.kou
 * @createTime 2020年05月25日 20:44:00
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcRequestHandler rpcRequestHandler;

    public NettyServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof RpcMessage) {
                log.info("server receive msg: [{}] ", msg);
                byte messageType = ((RpcMessage) msg).getMessageType();
                if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                    RpcMessage rpcMessage = new RpcMessage();
                    rpcMessage.setCodec(SerializationTypeEnum.KYRO.getCode());
                    rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                    rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                    rpcMessage.setData(RpcConstants.PONG);
                    ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    RpcRequest rpcRequest = (RpcRequest) ((RpcMessage) msg).getData();
                    // 执行目标方法  以及 得到目标方法的执行结果
                    Object result = rpcRequestHandler.handle(rpcRequest);
                    log.info(String.format("server get result: %s", result.toString()));
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        RpcResponse<Object> rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                        RpcMessage rpcMessage = new RpcMessage();
                        rpcMessage.setCodec(SerializationTypeEnum.KYRO.getCode());
                        rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                        rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                        rpcMessage.setData(rpcResponse);
                        ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    } else {
                        RpcResponse<Object> rpcResponse = RpcResponse.fail(RpcResponseCodeEnum.FAIL);
                        RpcMessage rpcMessage = new RpcMessage();
                        rpcMessage.setCodec(SerializationTypeEnum.KYRO.getCode());
                        rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                        rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                        rpcMessage.setData(rpcResponse);
                        ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        log.error("not writable now, message dropped");
                    }
                }

            }
        } finally {
            //Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("idle check happen, so close the connection");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
