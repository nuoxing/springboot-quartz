package com.quart.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JobAop {

	
	    @Pointcut("execution(public * com.quart.job.*.*(..))")
	    //@Pointcut("execution(public * com.aspect.quartzdemo.service.*.*(..))")
	    public void joinPointExpression(){}


	    @org.aspectj.lang.annotation.Before("joinPointExpression()")
	    public void before(){
	        System.out.println("start before1");
	    }
	    
	    
	    @Pointcut("execution(public * com.quart.service.*.*(..))")
	    //@Pointcut("execution(public * com.aspect.quartzdemo.service.*.*(..))")
	    public void joinPointExpression2(){}


	    @org.aspectj.lang.annotation.Before("joinPointExpression2()")
	    public void before2(){
	        System.out.println("start before3");
	    }


	
}
