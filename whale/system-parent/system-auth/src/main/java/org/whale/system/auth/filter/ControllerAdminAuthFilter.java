package org.whale.system.auth.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.whale.system.base.UserContext;
import org.whale.system.common.exception.ForbidVisitException;
import org.whale.system.common.exception.NotLoginException;
import org.whale.system.common.util.ThreadContext;

/**
 * 管理员权限标签过滤
 *
 * @author 王金绍
 * @date 2015年1月5日 下午5:22:04
 */
@Component
@Aspect
public class ControllerAdminAuthFilter {

	@Pointcut("@annotation(org.whale.system.auth.annotation.AdminAuth)")
	public void adminAuthAspect() {
	}
	
	@Before("adminAuthAspect()")
	public void doBefore(JoinPoint joinPoint) {
		UserContext uc = (UserContext)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		if(uc == null){
			throw new NotLoginException("用户未登录！");
		}
		if(!uc.isSuperAdmin()){
			throw new ForbidVisitException("无权限访问");
		}
		
	}
}