package com.su.scheduled;

import com.su.service.StatisticsDailyService;
import com.su.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//定时任务类
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

    //每天凌晨一点执行数据统计分析
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task(){
        //获取昨天数据
        String day= DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStaDaily(day);
        System.out.println("生成数据成功："+day);
    }
}
