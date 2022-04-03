package com.jaiser.qqrob.domain;

import love.forte.simbot.BotManager;
import love.forte.simbot.OriginBotManager;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 机器人对象
 *
 * @author Jaiser on 2022/4/3
 */
public class Bot {

    private static love.forte.simbot.Bot bot = null;

    /**
     * 懒汉加载，唯一单例对象
     * @return
     */
    public static love.forte.simbot.Bot INSTANCE() {
        if (bot == null) {
            OriginBotManager manager = OriginBotManager.INSTANCE;
            for(BotManager<?> it : manager) {
                System.out.println("BotManager: " + it);
                bot = it.all().collect(Collectors.toList()).get(0);
            }
        }
        return bot;
    }
}