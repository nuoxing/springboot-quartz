package com.quart.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.PostConstruct;

import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quart.util.QuartzManager;



/**
 * 定时任务 添加类
 * @author Administrator
 *
 */
@Service
public class JobHanderService {

	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	QuartzManager quartzManager;
	
	/**
	 * 往quartz中增加定时任务
	 */
	@PostConstruct //在bean创建完成并赋值完成后，进行的初始化操作
	public void addJobToQuartz(){
		
		try {
			addLockUpUnLockJob();
			addQdApprovalJob();
			addQdPdfCreateJob();
			addJyzxSynJob();
			addJyzxSendMsgJob();
			addJyzxSend2SJJob();
			addDisciplineSendJob();
			addDisciplineGetTokenJob();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error("定时任务报错，message="+sw.toString());
			
		}
	}
	
	

	private void addDisciplineGetTokenJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_DisciplineGetTokenJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_DisciplineGetTokenJob", "jobGroupName", "triggerName_DisciplineGetTokenJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.DisciplineGetTokenJob");
			quartzManager.addJob("jobName_DisciplineGetTokenJob", "jobGroupName", "triggerName_DisciplineGetTokenJob", "triggerGroupName",
					clazz , " 0 0/55 * ? * *", null);
		}else{
		
		}
	}
}
