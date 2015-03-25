package org.whale.inf.example.netty.sever;

import org.whale.inf.example.netty.common.Header;
import org.whale.inf.example.netty.common.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 响应心跳
 *
 * @author 王金绍
 * @Date 2015年3月24日 下午4:15:53
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		NettyMessage message = (NettyMessage)msg;
		if(message != null && message.getHeader() != null){
			if(message.getHeader().getType() == Header.HEART_BEAT_REQ){
				NettyMessage resMesg = new NettyMessage();
				Header header = new Header();
				header.setSessionId(message.getHeader().getSessionId());
				header.setType(Header.HEART_BEAT_RESP);
				
				resMesg.setHeader(header);
				
				System.out.println("服务端返回心跳...");
				ctx.writeAndFlush(resMesg);
			}else{
				ctx.fireChannelRead(msg);
			}
		}else{
			ctx.fireChannelRead(msg);
		}
	}

	
}