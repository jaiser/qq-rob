package com.jaiser.qqrob.utils;

import jdk.nashorn.internal.ir.IfNode;
import love.forte.simbot.Bot;
import love.forte.simbot.BotManager;
import love.forte.simbot.OriginBotManager;

import java.util.stream.Collectors;

/**
 * 机器对象工具
 *
 * @author Jaiser on 2022/4/3
 */
public class BotUtil {

    private static Bot bot = null;

    public Bot INSTANCE() {
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