/*
 * 文件名：QuartzManager.java 版权：Copyright by www.chinauip.com 描述： 修改人：jiangguofu 修改时间：2017-12-5 跟踪单号：
 * 修改单号： 修改内容：
 */
package com.quart.util;


import java.util.Date;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * 定时任务动态增加删除修改 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author jiangguofu
 * @version 2017年12月20日
 * @see QuartzManager
 * @since
 */

public class QuartzManager
{
    /**
     * 定时任务调度对象
     */
    private Scheduler scheduler;
    
    public QuartzManager(){
    	//System.out.println("init");
    }

    public void setScheduler(Scheduler scheduler)
    {
        this.scheduler = scheduler;
    }

    /**
     * @Description: 添加一个定时任务
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务
     * @param cron
     *            时间设置，参考quartz说明文档
     * @param data
     *            需要传入任务的数据
     * @return 
     * @throws SchedulerException
     */

    public boolean addJob(String jobName, String jobGroupName, String triggerName,
                              String triggerGroupName, Class jobClass, String cron, Object data)
        throws SchedulerException
    {

        // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,
            jobGroupName).build();
        if (data != null)
        {
            jobDetail.getJobDataMap().put("data", data);
        }
        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        // 触发器名,触发器组
        triggerBuilder.withIdentity(triggerName, triggerGroupName);
        triggerBuilder.startNow();
        // 触发器时间设定
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
        // 创建Trigger对象
        CronTrigger trigger = (CronTrigger)triggerBuilder.build();

        // 调度容器设置JobDetail和Trigger
        Date date = scheduler.scheduleJob(jobDetail, trigger);

        if (date != null)
        {
            // 启动
            if (!scheduler.isShutdown())
            {
                scheduler.start();
            }
            return true;
        }
        return false;
    }

    /**
     * @Description: 修改一个任务的触发时间
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param cron
     *            时间设置，参考quartz说明文档
     * @return 
     * @throws SchedulerException
     */
    public boolean modifyJob(String jobName, String jobGroupName, String triggerName,
                                 String triggerGroupName, String cron)
        throws SchedulerException
    {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
        if (trigger == null)
        {
            return false;
        }
        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron))
        {
            /** 方式一 ：调用 rescheduleJob 开始 */
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            trigger = (CronTrigger)triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            Date date = scheduler.rescheduleJob(triggerKey, trigger);
            /** 方式一 ：调用 rescheduleJob 结束 */

            /** 方式二：先删除，然后在创建一个新的Job */
            /*
             * JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
             * Class<? extends Job> jobClass = jobDetail.getJobClass(); removeJob(jobName,
             * jobGroupName, triggerName, triggerGroupName); addJob(jobName, jobGroupName,
             * triggerName, triggerGroupName, jobClass, cron,endTime);
             */
            /** 方式二 ：先删除，然后在创建一个新的Job */
            if (date != null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: 移除一个任务
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @return 
     * @throws SchedulerException
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName,
                                 String triggerGroupName)
        throws SchedulerException
    {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

        scheduler.pauseTrigger(triggerKey);// 停止触发器
        scheduler.unscheduleJob(triggerKey);// 移除触发器
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
    }

    /**
     * 描述: 暂停任务<br> 1、…<br> 2、…<br> 实现: <br> 1、…<br> 2、…<br>
     * 
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @return 
     * @throws SchedulerException
     * @see
     */
    public boolean pauseJob(String jobName, String jobGroupName)
        throws SchedulerException
    {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        if (jobKey != null)
        {
            scheduler.pauseJob(jobKey);
            return true;
        }
        return false;
    }

    /**
     * 描述:恢复任务 <br> 1、…<br> 2、…<br> 实现: <br> 1、…<br> 2、…<br>
     * 
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @return 
     * @throws SchedulerException
     * @see
     */
    public boolean resumeJob(String jobName, String jobGroupName)
        throws SchedulerException
    {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        if (jobKey != null)
        {
            scheduler.resumeJob(jobKey);
            return true;
        }
        return false;
    }

    /**
     * 描述:立即运行一次<br> 1、…<br> 2、…<br> 实现: <br> 1、…<br> 2、…<br>
     * 
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @return 
     * @throws SchedulerException
     * @see
     */
    public boolean runJobNoW(String jobName, String jobGroupName)
        throws SchedulerException
    {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        if (jobKey != null)
        {
            scheduler.triggerJob(jobKey);
            return true;
        }
        return false;
    }

    /**
     * @throws SchedulerException
     * @Description:启动所有定时任务
     */
    public void startJobs()
        throws SchedulerException
    {
        scheduler.start();
    }

    /**
     * @throws SchedulerException
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs()
        throws SchedulerException
    {
        if (!scheduler.isShutdown())
        {
            scheduler.shutdown();
        }
    }

    /**
     * 描述: 获取调度器<br> 1、…<br> 2、…<br> 实现: <br> 1、…<br> 2、…<br>
     * 
     * @return Scheduler
     * @throws SchedulerException
     * @see
     */
  /*  public Scheduler getScheduler()
        throws SchedulerException
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        return scheduler;
    }*/
    /**
     * 
     * 描述: 获取任务状态<br>
     * 1、…<br>
     * 2、…<br>
     * 实现: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param triggerName
     * @param triggerGroupName
     * @return
     * @throws SchedulerException 
     * @see
     */
    public TriggerState getJobState(String triggerName,String triggerGroupName) throws SchedulerException
    {
        
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        return scheduler.getTriggerState(triggerKey);
    }
    /**
     * 
     * 描述:获取 触发器<br>
     * 1、…<br>
     * 2、…<br>
     * 实现: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param triggerName
     * @param triggerGroupName
     * @return
     * @throws SchedulerException 
     * @see
     */
    public CronTrigger getTrigger(String triggerName, String triggerGroupName) throws SchedulerException
    {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
        return trigger;
    }
}
