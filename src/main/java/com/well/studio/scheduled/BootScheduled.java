package com.well.studio.scheduled;

import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: well-spring-boot-demo
 * @description: Springboot自带job任务
 * @author: daiyunbo
 * @create: 2020-07-31 10:29
 **/
@Component
public class BootScheduled {
    /**
     * "0 0 12 * * ?" 每天中午12点触发
     * "0 15 10 ? * *" 每天上午10:15触发
     * "0 15 10 * * ?" 每天上午10:15触发
     * "0 15 10 * * ? *" 每天上午10:15触发
     * "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
     * "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
     * "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
     * "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     * "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
     * "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
     * "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
     * "0 15 10 15 * ?" 每月15日上午10:15触发
     * "0 15 10 L * ?" 每月最后一日的上午10:15触发
     * "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
     * "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
     * "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
     */

    /**
     * Corn表达式 @Scheduled(cron = Corn表达式)
     */
    @Scheduled(cron = "0 37 10 * * ?")
    public void handleOrderStatus() {
        //定时任务代码
        System.out.println("just job");
    }


    public final static long SECOND = 1 * 1000;
    FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 固定等待时间 @Scheduled(fixedDelay = 时间间隔 )
     */
    @Scheduled(fixedDelay = SECOND * 20)
    public void fixedDelayJob() throws InterruptedException {
        System.out.println("[Scheduled 固定等待时间]"+fdf.format(new Date()));
    }

    /**
     * 固定间隔时间 @Scheduled(fixedRate = 时间间隔 )
     */
    @Scheduled(fixedRate = SECOND * 10)
    public void fixedRateJob() {
        System.out.println("[Scheduled 固定间隔时间]"+fdf.format(new Date()));
    }
}
