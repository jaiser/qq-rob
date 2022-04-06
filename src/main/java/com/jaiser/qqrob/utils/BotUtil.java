package com.jaiser.qqrob.utils;

import love.forte.simbot.Bot;
import love.forte.simbot.BotManager;
import love.forte.simbot.OriginBotManager;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 机器人对象
 *
 * @author Jaiser on 2022/4/3
 */
@Component
public class BotUtil {

    private Bot bot = null;

    /**
     * 获取机器人对象
     * @return
     */
    public Bot INSTANCE() {
        if (bot == null) {
            OriginBotManager manager = OriginBotManager.INSTANCE;
            for(BotManager<?> it : manager) {
                bot = it.all().collect(Collectors.toList()).get(0);
            }
        }
        return bot;
    }
}