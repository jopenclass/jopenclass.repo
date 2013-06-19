package org.jopenclass.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author madhumal
 * 
 */
@Aspect
public class DbMethodCallLogger {

	static Log log = LogFactory.getLog(DbMethodCallLogger.class.getName());

	/**
	 * 
	 * @param joinPoint
	 */
	@After("execution(* org.jopenclass.dao.*.*(..))")
	public void logAfterDbcallMethod(JoinPoint joinPoint) {

		String msg = "called the method : "
				+ joinPoint.getSignature()
				+ " by "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName();
		log.info(msg);
	}

	/**
	 * 
	 * @param joinPoint
	 * @param error
	 */
	@AfterThrowing(pointcut = "execution(* org.jopenclass.dao.*.*(..))", throwing = "error")
	public void logAfterDbCallMethodThrowing(JoinPoint joinPoint,
			Throwable error) {

		String msg = "exception thrown at : " + joinPoint.getSignature()
				+ " Exception : "
				+ error;
		log.error(msg, error);
	}
}
