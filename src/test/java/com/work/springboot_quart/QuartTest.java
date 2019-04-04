package com.work.springboot_quart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quart.util.QuartzManager;


@RunWith (SpringRunner.class)
@SpringBootTest
public class QuartTest {
	
	@Autowired
	QuartzManager quartzManager;
	
	
	//执行多次这个单元测试 模拟测试 对多应用 重启的情况 可能出现的情况 是 第一个应用跑任务  第二个任务 删除数据 然后又添加 
	//这时第一个应用会停止任务 第二个执行任务
	//这时因为 第二个应用删除之前的任务 这时它检测到
	//ClusterManager: detected 1 failed or restarted instances.

    //ClusterManager: Scanning for instance "USER-20151010LK1554255737365"'s failed in-progress jobs.
	
	//然后自己执行任务
	@Test
	public void test() throws Exception{
		quartzManager.removeJob("jobName", "jobGroupName", "triggerName", "triggerGroupName");
		//quartzManager.removeJob("jobName2", "jobGroupName2", "triggerName2", "triggerGroupName2");
		TriggerState state = quartzManager.getJobState("triggerName", "triggerGroupName");
		if (state!=TriggerState.NORMAL){
			Class clazz = Class.forName("com.quart.job.HelloWorldJob");
			quartzManager.addJob("jobName", "jobGroupName", "triggerName", "triggerGroupName",
					clazz , "0/5 * * * * ?", null);
		}
	/*	quartzManager.addJob("jobName2", "jobGroupName2", "triggerName2", "triggerGroupName2",
				clazz , "0/60 * * * * ?", null);*/
		Thread.sleep(5000000);
		//quartzManager.removeJob("jobName", "jobGroupName", "triggerName", "triggerGroupName");
		//System.out.println("移出");
		//Thread.sleep(5000);
	}
	
	//测试 一个应用跑着定时任务 然后另一个应用删除任务 就是删除表的数据 情况
	//结果是 跑着任务的应用 会检测到 任务 失效  输出  
	//ClusterManager: detected 1 failed or restarted instances.
	//ClusterManager: Scanning for instance "****-test1.1384490***726"'s failed in-progress jobs.
	//然后 之前跑着任务的应用 就是停止 任务 不再执行的了
	@Test
	public void test2() throws SchedulerException, InterruptedException{
		//System.out.println("移出");
		//quartzManager.removeJob("jobName", "jobGroupName", "triggerName", "triggerGroupName");
		Thread.sleep(5000000);
	}
	
	
	@Test
	public void test3() throws SchedulerException{
		TriggerState state = quartzManager.getJobState("triggerName", "triggerGroupName");
		System.out.println(state);
	}
	
}

