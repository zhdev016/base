package org.whale.system.client;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whale.system.common.encrypt.EncryptUtil;
import org.whale.system.inf.ClientException;
import org.whale.system.inf.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 实际客户端接口处理
 * 
 * @author 王金绍
 * 2015年11月8日 上午12:35:11
 */
@Component
public class WspzClientInvokeHandler implements ClientInvokeHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(WspzClientInvokeHandler.class);
	
	AtomicLong seq = new AtomicLong(10000);
	
	@Autowired
	private ClientConf clientConf;

	public void beforeCall(ClientContext clientContext) {
		clientContext.setUrl(clientConf.getServerHost()+clientContext.getServiceUrl()+"/"+clientContext.getMethod().getName());
		clientContext.setReqno(clientConf.getAppId()+seq.incrementAndGet());
		
		Map<String, Object> params = clientContext.getParams();
		params.put("appId", clientConf.getAppId());
		params.put("sign", this.createSign(clientContext));
		params.put("reqno", clientContext.getReqno());
		
		if(logger.isDebugEnabled()){
			logger.debug(" beforeCall {}", clientContext);
		}
	}

	public void afterCall(ClientContext clientContext) {
		Object rs = clientContext.getRs();
		if(rs instanceof Result){
			Result<?> result = (Result<?>)rs;
			if(!clientContext.getMethod().getReturnType().equals(Result.class)){
				if(Result.SUCCESS_CODE.equals(result.getCode())){
					clientContext.setRs(result.getData());
				}else{
					if(logger.isDebugEnabled()){
						logger.debug(" afterCall 处理失败： {}", rs);
					}
					
					//TODO 根据不同编码，抛出业务异常与Client 或 Server异常
					throw new ClientException(result.getCode(), result.getMessage());
				}
			}
		}
		
	}

	/**
	 * 创建签名
	 * @param clientContext
	 * @return
	 */
	private String createSign(ClientContext clientContext){
		StringBuilder strb = new StringBuilder();
		strb.append(clientContext.getServiceUrl()+"/"+clientContext.getMethod().getName())
			.append(clientConf.getSignKey());
		if(clientContext.getArgs() != null ){
			if(clientContext.getReqStr() != null){
				strb.append(clientContext.getReqStr());
			}else{
				strb.append(JSON.toJSONString(clientContext.getArgs(), SerializerFeature.WriteClassName));
			}
		}
		try {
			String sign = EncryptUtil.md5(strb.toString().getBytes("utf-8"));
			if(logger.isDebugEnabled()){
				logger.debug("签名： {}, 签名字符串：{}", sign, strb.toString());
			}
			return sign;
		}catch(Exception e){
			logger.error("签名编码异常", e);
			throw new ClientException("签名编码异常", e);
		}
	}

	@Override
	public void onReqest(ClientContext clientContext) {
		
	}
}