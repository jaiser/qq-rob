package com.jaiser.qqrob;

import love.forte.simboot.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jaiser
 */
@EnableScheduling
@SpringBootApplication
@EnableSimbot
@MapperScan(basePackages = "com.jaiser.qqrob.mapper") //扫描所有的mapper接口并创建代理类
public class QqRobApplication {

    private static final Logger logger = LoggerFactory.getLogger(QqRobApplication.class);

    public static void main(String[] args) {
//        try {
////            final SimbootContext context = SimbootApp.run(QqRobApplication.class, args);
////            context.joinBlocking();
//        } catch (SimbootApplicationException e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(QqRobApplication.class, args);

        startSuccess();
    }

    /**
     * 初始化启动日志
     */
    private static void startSuccess() {
        logger.info("=========================================");
        logger.info("=============启动成功，万事大吉=============");
        logger.info("=========================================");
//        try {
//            TimeUnit.SECONDS.sleep(15);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String[] managerList = RobConstant.INSTANCE().getManager().split(",");
//        for (String manager:
//                managerList) {
//            Bot.INSTANCE().getFriend(Identifies.ID(manager)).sendBlocking("机器人启动成功！");
//        }
    }

}
