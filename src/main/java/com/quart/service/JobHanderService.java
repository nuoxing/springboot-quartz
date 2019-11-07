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
	
	
	private void addJyzxSend2SJJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_JyzxSend2SJJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_JyzxSend2SJJob", "jobGroupName", "triggerName_JyzxSend2SJJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.JyzxSend2SJJob");
			quartzManager.addJob("jobName_JyzxSend2SJJob", "jobGroupName", "triggerName_JyzxSend2SJJob", "triggerGroupName",
					clazz , "0 0 0/4 ? * *", null);
		}else{
			System.out.println("简易注销同步省局任务存在，不再创建");
		}
	}


	/**
	 * 业务锁定解锁
	 * 每天早上6点一次 0 0 6 * * ?
	 * @throws Exception
	 */
	private void addLockUpUnLockJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_LockUpUnLockJob", "triggerGroupName");
	   if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_LockUpUnLockJob", "jobGroupName", "triggerName_LockUpUnLockJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.LockUpUnLockJob");
			quartzManager.addJob("jobName_LockUpUnLockJob", "jobGroupName", "triggerName_LockUpUnLockJob", "triggerGroupName",
					clazz , "0 0 6 * * ?", null);
		}else{
			System.out.println("业务锁定解锁任务存在，不再创建");
		}
		
		
	}
	
	
	/**
	 * 全电流程审批
	 * 每1分钟 0 0/1 * ? * *
	 * @throws Exception
	 */
	private void addQdApprovalJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_QdApprovalJob", "triggerGroupName");
	   if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_QdApprovalJob", "jobGroupName", "triggerName_QdApprovalJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.QdApprovalJob");
			quartzManager.addJob("jobName_QdApprovalJob", "jobGroupName", "triggerName_QdApprovalJob", "triggerGroupName",
					clazz , "0 0/1 * ? * *", null);
		}else{
			System.out.println("全电流程审批任务存在，不再创建");
		}
		
	}
	
	
	/**
	 * 全电pdf创建
	 * 每5分钟 0 0/5 * ? * *
	 * @throws Exception
	 */
	private void addQdPdfCreateJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_QdPdfCreateJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_QdPdfCreateJob", "jobGroupName", "triggerName_QdPdfCreateJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.QdPdfCreateJob");
			quartzManager.addJob("jobName_QdPdfCreateJob", "jobGroupName", "triggerName_QdPdfCreateJob", "triggerGroupName",
					clazz , "0 0 3,12,21 * * ?", null);
		}else{
			System.out.println("全电创建PDF任务存在，不再创建");
		}
	}
	
	/**
	 * 简易注销同步数据 
	 * 每4小时 0 0 0/4 ? * *
	 * @throws Exception
	 */
	private void addJyzxSynJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_JyzxSynJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_JyzxSynJob", "jobGroupName", "triggerName_JyzxSynJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.JyzxSynJob");
			quartzManager.addJob("jobName_JyzxSynJob", "jobGroupName", "triggerName_JyzxSynJob", "triggerGroupName",
					clazz , "0 0 0/4 ? * *", null);
		}else{
			System.out.println("简易注销同步数据任务存在，不再创建");
		}
	}
	
	/**
	 * 简易注销发短信
	 * 每4小时 0 0 0/4 ? * *
	 * @throws Exception
	 */
	private void addJyzxSendMsgJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_JyzxSendMsgJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_JyzxSendMsgJob", "jobGroupName", "triggerName_JyzxSendMsgJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.JyzxSendMsgJob");
			quartzManager.addJob("jobName_JyzxSendMsgJob", "jobGroupName", "triggerName_JyzxSendMsgJob", "triggerGroupName",
					clazz , "0 0 0/4 ? * *", null);
		}else{
			System.out.println("简易注销发短信任务存在，不再创建");
		}
	}
	
	
	
	/**
	 * 联合惩戒数据反馈任务
	 * 每6小时 0 0 0/6 ? * *
	 * @throws Exception
	 */
	private void addDisciplineSendJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_DisciplineSendJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_DisciplineSendJob", "jobGroupName", "triggerName_DisciplineSendJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.DisciplineSendJob");
			quartzManager.addJob("jobName_DisciplineSendJob", "jobGroupName", "triggerName_DisciplineSendJob", "triggerGroupName",
					clazz , "0 0 0/6 ? * *", null);
		}else{
			System.out.println("联合惩戒数据反馈任务存在，不再创建");
		}
	}
	

	/**
	 * 联合惩戒token获取任务
	 * 每55分钟 0 0/55 * ? * *
	 * @throws Exception
	 */
	private void addDisciplineGetTokenJob() throws Exception{
		TriggerState state = quartzManager.getJobState("triggerName_DisciplineGetTokenJob", "triggerGroupName");
	    if (state!=TriggerState.NORMAL){
			quartzManager.removeJob("jobName_DisciplineGetTokenJob", "jobGroupName", "triggerName_DisciplineGetTokenJob", "triggerGroupName");
			Class clazz = Class.forName("com.aisino.aic.quartz.job.DisciplineGetTokenJob");
			quartzManager.addJob("jobName_DisciplineGetTokenJob", "jobGroupName", "triggerName_DisciplineGetTokenJob", "triggerGroupName",
					clazz , " 0 0/55 * ? * *", null);
		}else{
			System.out.println("联合惩戒获取token任务存在，不再创建");
		}
	}
}
