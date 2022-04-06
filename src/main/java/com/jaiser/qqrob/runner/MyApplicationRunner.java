package com.jaiser.qqrob.runner;

import com.jaiser.qqrob.constant.RobConstant;
import com.jaiser.qqrob.utils.BotUtil;
import love.forte.simbot.Identifies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 自定义启动器
 *
 * @author Jaiser on 2022/4/6
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private RobConstant robConstant;

    @Autowired
    private BotUtil botUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] managerList = robConstant.getManager().split(",");
        for (String manager:
                managerList) {
            botUtil.INSTANCE().getFriend(Identifies.ID(manager)).sendBlocking("机器人启动成功");
        }
    }


}