package com.jaiser.qqrob.listener;

import com.jaiser.qqrob.constant.RobConstant;
import com.jaiser.qqrob.enums.GroupOperateEnum;
import com.jaiser.qqrob.listener.group.MyGroupUtil;
import com.jaiser.qqrob.service.AutoReplyService;
import love.forte.di.annotation.Beans;
import love.forte.di.annotation.Depend;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.component.mirai.event.MiraiReceivedMessageContent;
import love.forte.simbot.message.*;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 群相关事件
 *
 * @author ForteScarlet
 */
@Beans
public class MyGroupListener {

    private static final Logger logger = LoggerFactory.getLogger("群消息");

    @Autowired
    private MyGroupUtil myGroupUtil;

    /**
     * 这里监听的是mirai组件所提供的特殊事件类型，
     * 一个组件的特殊事件能够提供更丰富的特性，并且更有针对性。
     * <p>
     * <p>
     * 此事件在控制台打印所有的群消息，不做其他操作。
     *
     * @param event 事件本体
     */
    @Listener
    public void myGroupListen(MiraiGroupMessageEvent event) {
        // 事件发生的群
        final String groupName = event.getGroup().getName();
        final String authorName = event.getAuthor().getUsername();
        final MiraiReceivedMessageContent messageContent = event.getMessageContent();
        // nativeMessageChain是mirai中的原生事件对象
        // 只有在使用mirai组件下的特殊事件类型的时候才会有
        final MessageChain nativeMessageChain = messageContent.getNativeMessageChain();
        // 这里直接展示mirai的原生消息对象
        logger.warn("「{}」在「{}」里发送了消息：{}", authorName, groupName, nativeMessageChain);

        String msg = event.getMessageContent().getPlainText();
        String[] msgList = msg.split("\\s+");
        if (GroupOperateEnum.ORDER_LIST.getValue().equals(msgList[0])) {
            myGroupUtil.listAllOrder(event);
        } else if (GroupOperateEnum.ALL_ENTRY.getValue().equals(msgList[0])) {
            myGroupUtil.listAllReply(event);
        } else if (GroupOperateEnum.STUDY_ENTRY.getValue().equals(msgList[0])) {
            myGroupUtil.studyEntry(event);
        } else if (GroupOperateEnum.UPDATE_ENTRY.getValue().equals(msgList[0])) {
            myGroupUtil.updateEntry(event);
        }else if (GroupOperateEnum.DECRYPT_JASYPT.getValue().equals(msgList[0])) {
            myGroupUtil.decryptByJasypt(event);
        }else if (GroupOperateEnum.ENCRYPT_JASYPT.getValue().equals(msgList[0])) {
            myGroupUtil.encryptByJasypt(event);
        } else {
            myGroupUtil.replyEntry(event);
        }
    }






    /*
    List<Message.Element<?>> messageList = new ArrayList<>(3);
messageList.add(Text.of("simbot"));
messageList.add(new At(Identifies.ID(123)));
messageList.add(AtAll.INSTANCE);

// 通过列表得到消息链
final Messages messagesOfList = Messages.listToMessages(messageList);

// 注意! Messages 不允许直接的修改操作
// messagesOfList.add(AtAll.INSTANCE);

// 需要通过 plus 得到新的消息链
final Messages newMessagesOfList = messagesOfList.plus(AtAll.INSTANCE);

// 通过 Messages.getMessages 得到消息链
final Messages messages = Messages.getMessages(Text.of("forte"), new At(Identifies.ID(114514)), AtAll.INSTANCE);

     */

}
