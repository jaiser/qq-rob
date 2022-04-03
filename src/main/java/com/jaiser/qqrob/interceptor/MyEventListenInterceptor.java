package com.jaiser.qqrob.interceptor;

import com.jaiser.qqrob.constant.RobConstant;
import javafx.event.EventType;
import love.forte.di.annotation.Beans;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.event.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 事件监听拦截器
 *
 * @author Jaiser on 2022/4/3
 */
@Beans
public class MyEventListenInterceptor implements BlockingEventListenerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("事件监听拦截器");

    @NotNull
    @Override
    public EventResult doIntercept(@NotNull Context context) {

        if (context.getListener().isTarget(MiraiGroupMessageEvent.Key)) {
            if (!RobConstant.INSTANCE().isGroupEnable()) {
                return EventResult.Invalid.INSTANCE;
            }
        }
        return context.proceedBlocking();
    }
}