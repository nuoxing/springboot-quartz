/*
 * 文件名：TimedTaskUtil.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：jiangguofu
 * 修改时间：2018年4月28日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.quart.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 定时任务工具类
 * @author 
 * @version 2018年4月28日
 * @see TimedTaskUtil
 * @since
 */

public class TimedTaskUtil
{

    /**
     * 
     * 描述: 判断当前时间是否在规定时间内<br>
     * 1、HH:mm<br>
     * 2、…<br>
     * 实现: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return true/false
     * @throws ParseException 
     * @see
     */
    public static boolean isBelong(String startTime,String endTime) throws ParseException
    {
        if (StringUtils.isBlank(startTime))
        {
            return false;
        }
        if (StringUtils.isBlank(endTime))
        {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = df.parse(df.format(new Date()));
        Date start = df.parse(startTime);
        Date end = df.parse(endTime);
        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(now);
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(end);
        if (nowDate.after(startDate) && nowDate.before(endDate))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 获得提交+延迟计算后的时间
     * 
     * @param subTime
     *            提交时间
     * @param delay
     *            延迟时间
     * @return Date=提交时间+延迟时间
     */
    public static Date subTimePlusDelay(Date subTime, Integer delay)
    {
        if (delay == null)
        {
            delay = 30;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(subTime);
        cal.add(Calendar.MINUTE, delay);
        Date date = cal.getTime();
        return date;
    }
    
    /**
     * 获取年月日
     * 
     * @return String今天年月日
     */
    public static String getYMD()
    {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    /**
     * 
     * 描述:获取今天为周几 <br>
     * 1、…<br>
     * 2、…<br>
     * 实现: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @return 周几
     * @see
     */
    public static String getWeek()
    {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("EEEE");
        return format.format(date);
    }
}
