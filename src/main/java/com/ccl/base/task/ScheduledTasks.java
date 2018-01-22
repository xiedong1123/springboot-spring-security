package com.ccl.base.task;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    //@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
    	logger.error("错误, 这不是个归路...");
        System.out.println("当前时间：" + dateFormat.format(new Date()));
    }

}