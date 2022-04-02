package com.jaiser.qqrob;

import love.forte.simboot.SimbootApp;
import love.forte.simboot.SimbootApplicationException;
import love.forte.simboot.SimbootContext;
import love.forte.simboot.core.SimbootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jaiser
 */
@SpringBootApplication
@SimbootApplication
@MapperScan(basePackages = "com.jaiser.qqrob.mapper") //扫描所有的mapper接口并创建代理类
public class QqRobApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(QqRobApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QqRobApplication.class, args);
        try {
            final SimbootContext context = SimbootApp.run(QqRobApplication.class, args);
            context.joinBlocking();
        } catch (SimbootApplicationException e) {
            e.printStackTrace();
        }
        LOGGER.info("=========================================");
        LOGGER.info("=============启动成功，万事大吉=============");
        LOGGER.info("=========================================");
    }

}
