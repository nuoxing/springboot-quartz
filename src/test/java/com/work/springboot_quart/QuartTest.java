package com.work.springboot_quart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quart.util.QuartzManager;


@RunWith (SpringRunner.class)
@SpringBootTest
public class QuartTest {
	
	@Autowired
	QuartzManager quartzManager;
	
	
	@Test
	public void test() throws Exception{
		
		/*Class clazz = Class.forName("com.quart.job.HelloWorldJob");
		quartzManager.addJob("jobName", "jobGroupName", "triggerName", "triggerGroupName",
				clazz , "0/10 * * * * ?", null);
		quartzManager.addJob("jobName2", "jobGroupName2", "triggerName2", "triggerGroupName2",
				clazz , "0/10 * * * * ?", null);*/
		Thread.sleep(500000);
		//quartzManager.removeJob("jobName", "jobGroupName", "triggerName", "triggerGroupName");
		//System.out.println("移出");
		//Thread.sleep(5000);
	}
	
}

