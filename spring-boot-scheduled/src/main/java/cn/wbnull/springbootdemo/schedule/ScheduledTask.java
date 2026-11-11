package cn.wbnull.springbootdemo.schedule;

import cn.wbnull.springbootdemo.util.LoggerUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author dukunbiao(null)  2019-02-18
 * https://github.com/dkbnull/SpringBootDemo
 */
@Component
public class ScheduledTask {

//    @Scheduled(cron = "0/10 * * * * ?") //每10秒执行一次
//    public void scheduledTaskByCorn() {
//        LoggerUtils.info("定时任务开始 ByCorn：" + DateUtils.dateFormat());
//        scheduledTask();
//        LoggerUtils.info("定时任务结束 ByCorn：" + DateUtils.dateFormat());
//    }

//    @Scheduled(fixedRate = 10000) //每10秒执行一次
//    public void scheduledTaskByFixedRate() {
//        LoggerUtils.info("定时任务开始 ByFixedRate：" + DateUtils.dateFormat());
//        scheduledTask();
//        LoggerUtils.info("定时任务结束 ByFixedRate：" + DateUtils.dateFormat());
//    }

//    @Scheduled(fixedDelay = 10000) //每10秒执行一次
//    public void scheduledTaskByFixedDelay() {
//        LoggerUtils.info("定时任务开始 ByFixedDelay：" + DateUtils.dateFormat());
//        scheduledTask();
//        LoggerUtils.info("定时任务结束 ByFixedDelay：" + DateUtils.dateFormat());
//    }

//    @Scheduled(cron = "${demo.corn}")
//    public void scheduledTaskByConfig() {
//        LoggerUtils.info("定时任务 ByConfig：" + DateUtils.dateFormat());
//    }

    private void scheduledTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LoggerUtils.error("定时任务线程被中断", e);
            Thread.currentThread().interrupt();
        }
    }
}
