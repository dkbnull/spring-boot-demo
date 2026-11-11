package cn.wbnull.springbootdemo.schedule;

import cn.wbnull.springbootdemo.util.DateUtils;
import cn.wbnull.springbootdemo.util.LoggerUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author dukunbiao(null)  2019-02-18
 * https://github.com/dkbnull/SpringBootDemo
 */
@Component
@EnableAsync
public class ScheduledTaskV3 {

    @Scheduled(cron = "0/7 * * * * ?")
    @Async
    public void scheduledTaskV1() {
        LoggerUtils.info("定时任务V3，定时任务1开始：" + DateUtils.dateFormat());
        scheduledTask();
        LoggerUtils.info("定时任务V3，定时任务1结束：" + DateUtils.dateFormat());
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void scheduledTaskV2() {
        LoggerUtils.info("定时任务V3，定时任务2开始：" + DateUtils.dateFormat());
        scheduledTask();
        LoggerUtils.info("定时任务V3，定时任务2结束：" + DateUtils.dateFormat());
    }

    @Scheduled(cron = "0/22 * * * * ?")
    @Async
    public void scheduledTaskV3() {
        LoggerUtils.info("定时任务V3，定时任务3开始：" + DateUtils.dateFormat());
        scheduledTask();
        LoggerUtils.info("定时任务V3，定时任务3结束：" + DateUtils.dateFormat());
    }

    private void scheduledTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LoggerUtils.error("定时任务线程被中断", e);
        }
    }
}
