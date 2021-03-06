package com.jukemap.biz.common;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import com.jukemap.biz.user.UserVO;

@Service
@Aspect
public class ServiceAdvice {
	@Around("ServicePointcut.serviceJukePointcut()")
	public Object jukeCheck(ProceedingJoinPoint pjp) throws Throwable {
		for(Object obj : pjp.getArgs()) {
			if (obj instanceof HttpSession) {
				UserVO user = (UserVO) ((HttpSession) obj).getAttribute("user");
				if(user == null) {
					return "redirect:badRs.do";
				}
			}
		}	
		return pjp.proceed();
	}

	@Around("ServicePointcut.serviceUserPointcut()")
	public Object loginCheck(ProceedingJoinPoint pjp) throws Throwable {
		
		if(!pjp.getSignature().getName().equals("logout")) {
			for(Object obj : pjp.getArgs()) {
				if (obj instanceof HttpSession) {
					UserVO user = (UserVO) ((HttpSession) obj).getAttribute("user");
					if(user != null) {
						return "redirect:jukeMap.do";
					}
				}
			}	
		}
		
		return pjp.proceed();
	}
}
