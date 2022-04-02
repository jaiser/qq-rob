package com.jaiser.qqrob.controller;

import love.forte.simbot.*;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 转发类
 *
 * @author Jaiser on 2022/4/2
 */
@RestController
@RequestMapping("/forward")
public class ForwardController {


    @PostMapping("/sendSms")
    public void sendSms() {
        Bot bot = null;
        OriginBotManager manager = OriginBotManager.INSTANCE;
        for(BotManager<?> it : manager) {
            System.out.println("BotManager: " + it);
            bot = it.all().collect(Collectors.toList()).get(0);
//            List<Bot> botList = it.all().limit(1).collect(Collectors.toList());
//            bot = botList.get(0);
//            bot = it.get(Identifies.ID("2195547463"));
        }
        // 得到对应组件下的所有manager。
// 实际上SimbotComponent不会有所属botManager，此处仅做示例。
//        final Stream<BotManager<?>> managers = manager.getManagers(SimbotComponent.INSTANCE);
        // 根据ID和组件信息得到对应的Bot。
//        Stream<BotManager<?>> managers = manager.getManagers(SimbotComponent.INSTANCE);
//        managers.map(e -> {
//            BotManager botManager = e;
//
//            return botManager;
//        });
//        BotManager<? extends Bot> manager1 = managers.collect(Collectors.toList()).get(0);
//        final BotManager<? extends Bot> manager = OriginBotManager.INSTANCE.getFirstManager(SimbotComponent.INSTANCE);
        // 根据ID获取对应的Bot
//        final Bot bot = manager1.get(Identifies.ID("2195547463"));

//        Bot bot = manager.getBot(Identifies.ID("2195547463"), SimbotComponent.INSTANCE);
//         Bot bot = managers.get(Identifies.ID(2195547463L));
//        sendIfSupportBlocking
//        originBotManager.getFirstManager(SimbotComponent.INSTANCE).getComponent();
        Message message = Messages.getMessages(Text.of("11111"));

        bot.getFriend(Identifies.ID(1007923707))
                .sendIfSupportBlocking(message);
    }
}