package com.jaiser.qqrob.listener;

import com.jaiser.qqrob.constant.RobConstant;
import com.jaiser.qqrob.enums.ManagerOperateEnum;
import love.forte.di.annotation.Beans;
import love.forte.simboot.annotation.ContentTrim;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.Identifies;
import love.forte.simbot.PriorityConstant;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.ContinuousSessionContext;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.jaiser.qqrob.enums.ManagerOperateEnum.*;

/**
 * 好友相关事件监听器。
 *
 * @author ForteScarlet
 */
@Beans
public class MyFriendListener {

    private static final Logger logger = LoggerFactory.getLogger("好友消息");

    @Autowired
    private RobConstant robConstant;
    /**
     * 监听好友消息，并且回复这个好友一句"是的"。
     *
     * @param event 监听的事件对象
     */
    @Listener
    public void friendListen(FriendMessageEvent event) {
        if (robConstant.isFriendEnable()) {
            Friend friend = event.getFriend();
//        logger.info("friend: {}({})", friend.getUsername(), friend.getId());
//        logger.info("message: {}", event.getMessageContent().getPlainText());
            // 如果是自己(管理员)的消息，则不回复
            if (robConstant.isManager(String.valueOf(friend.getId()))) {
                return;
            }
            if (event instanceof ReplySupport) {
                ((ReplySupport) event).replyBlocking("是的");
            } else {
                friend.sendBlocking("是的");
            }
        }
    }

    @Filter(value = "管理", matchType = MatchType.TEXT_CONTAINS)
    @ContentTrim // 将filter所需的匹配内容进行 trim 操作。
    @Listener(priority = PriorityConstant.PRIORITIZED_1)
    public void managerListen(FriendMessageEvent event, ContinuousSessionContext sessionContext) {
        Friend friend = event.getFriend();
        String value = null;
        // 如果是自己(管理员)的消息则进行操作
        if (robConstant.isManager(friend.getId().toString())) {
            List<Message.Element<?>> messageList = new ArrayList<>();
            for (ManagerOperateEnum managerOperateEnum : ManagerOperateEnum.values()) {
                messageList.add(Text.of(managerOperateEnum.getValue() + "." + managerOperateEnum.getDesc()));
                if (!"0".equals(managerOperateEnum.getValue())) {
                    messageList.add(Text.of("\n"));
                }
            }
            while (!"0".equals(value)) {
                Messages messagesOfList = Messages.listToMessages(messageList);
                friend.sendBlocking(messagesOfList);

                try {
                    value = sessionContext.waitingForOnMessage(
                            Identifies.randomID(),
                            30_000L,
                            event,
                            (sessionEvent, context, provider) -> {
                                // 得到下一个事件中的文本消息
                                final String plainText = sessionEvent.getMessageContent().getPlainText();

                                // 推送此文本
                                provider.push(plainText);
                            });
                } catch (Exception e) {
                    logger.error("操作异常", e);
                    friend.sendBlocking("操作超时，退出操作");
                    break;
                }
                if (StringUtils.isNotBlank(value) && !OPERATE_EXIT.getValue().equals(value)) {
                    if (managerOperate(value)) {
                        // 查看所有操作状态
                        if (OPERATE_STATE.getValue().equals(value)) {
                            friend.sendBlocking(getAllOperateState());
                        }else {
                            friend.sendBlocking(ManagerOperateEnum.getEnumByValue(value).getDesc() + "成功，请继续操作：");
                        }
                    } else {
                        friend.sendBlocking("操作异常，退出操作，请联系管理员查看！");
                    }
                }else {
                    friend.sendBlocking("退出操作成功");
                    break;
                }
            }
        }
    }

    /**
     * 管理员操作。
     *
     * @param value 事件对象
     */
    private Boolean managerOperate(String value) {
        switch (value) {
            case "0":
                // 退出操作
                return true;
            case "1":
                // 查看所有操作状态
                return true;
            case "2":
                // 开启监听好友功能
                robConstant.setFriendEnable(true);
                return true;
            case "3":
                // 关闭监听好友功能
                robConstant.setFriendEnable(false);
                return true;
            case "4":
                // 开启监听群组功能
                robConstant.setGroupEnable(true);
                return true;
            case "5":
                // 关闭监听群组功能
                robConstant.setGroupEnable(false);
                return true;

            default:
                return false;
        }
    }

    /**
     * 获取所有操作状态
     */
    private String getAllOperateState() {

        StringBuffer sb = new StringBuffer();
        sb.append("监听好友功能：").append(robConstant.isFriendEnable() ? "开启" : "关闭").append("\n");
        sb.append("监听群组功能：").append(robConstant.isGroupEnable() ? "开启" : "关闭");
        return sb.toString();
    }

}
