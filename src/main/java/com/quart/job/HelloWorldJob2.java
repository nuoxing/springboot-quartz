package com.quart.job;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quart.service.UserService;


@DisallowConcurrentExecution //作业不并发
@Component
public class HelloWorldJob2 implements Job{

   
	@Autowired
	UserService userService;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
        
        System.out.println("欢迎使用yyblog,这是一个定时任务  --小卖铺的老爷爷!2");
		
    }

}