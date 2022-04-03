package com.jaiser.qqrob;

import com.jaiser.qqrob.constant.RobConstant;
import com.jaiser.qqrob.domain.Bot;
import love.forte.simboot.SimbootApp;
import love.forte.simboot.SimbootApplicationException;
import love.forte.simboot.SimbootContext;
import love.forte.simboot.core.SimbootApplication;
import love.forte.simbot.Identifies;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jaiser
 */
@EnableScheduling
@SpringBootApplication
@SimbootApplication
@MapperScan(basePackages = "com.jaiser.qqrob.mapper") //扫描所有的mapper接口并创建代理类
public class QqRobApplication {

    private static final Logger logger = LoggerFactory.getLogger(QqRobApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QqRobApplication.class, args);
        startSuccess();
        try {
            final SimbootContext context = SimbootApp.run(QqRobApplication.class, args);
            context.joinBlocking();
        } catch (SimbootApplicationException e) {
            e.printStackTrace();
        }
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
