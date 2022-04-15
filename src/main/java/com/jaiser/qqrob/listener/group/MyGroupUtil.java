package com.jaiser.qqrob.listener.group;

import com.jaiser.qqrob.constant.CommonConstant;
import com.jaiser.qqrob.domain.AutoReplyD;
import com.jaiser.qqrob.enums.GroupOperateEnum;
import com.jaiser.qqrob.service.AutoReplyService;
import com.jaiser.qqrob.service.impl.AutoReplyServiceImpl;
import love.forte.di.annotation.Beans;
import love.forte.di.annotation.Depend;
import love.forte.simbot.ID;
import love.forte.simbot.Identifies;
import love.forte.simbot.action.ReplySupport;
import love.forte.simbot.component.mirai.MiraiGroup;
import love.forte.simbot.component.mirai.MiraiMember;
import love.forte.simbot.component.mirai.event.MiraiGroupMessageEvent;
import love.forte.simbot.message.At;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 自定义群组监听工具
 *
 * @author Jaiser on 2022/4/6
 */
@Component
public class MyGroupUtil {

    /**
     * 日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstant.DATE_HMYDHS);

    @Autowired
    public AutoReplyService autoReplyService;

    /**
     * jasypt对象
     */
    BasicTextEncryptor textEncryptor = null;

    /**
     * 列出所有指令
     *
     * @param event 事件
     */
    public void listAllOrder(MiraiGroupMessageEvent event) {
        StringBuffer sb = new StringBuffer("以下为目前可用指令：");
        for (GroupOperateEnum operateEnum :
                GroupOperateEnum.values()) {
//            if (operateEnum.isShow()) {
                sb.append("\n《" + operateEnum.getValue() + "》" + operateEnum.getRemark());
//            }
        }
        event.sendBlocking(sb.toString());
    }

    /**
     * 查询组词条
     *
     * @param event
     */
    public void replyEntry(MiraiGroupMessageEvent event) {
        // 事件发生的群
        final MiraiGroup groupInfo = event.getGroup();
        final MiraiMember authorInfo = event.getAuthor();

        AutoReplyD autoReplyD = autoReplyService.selectOneByKey(event.getMessageContent().getPlainText());
        if (autoReplyD != null) {
            // 参数1：回复的消息 参数2：是否at当事人
            Messages message = Messages.getMessages(new At(Identifies.ID(String.valueOf(authorInfo.getId()))), Text.of(autoReplyD.getChatValue()));
            event.sendBlocking(message);
        }
    }

    /**
     * 查询回复词条
     * @param event
     */
    public void listAllReply(MiraiGroupMessageEvent event) {
        AutoReplyD autoReplyD = new AutoReplyD();
        List<AutoReplyD> infoList = autoReplyService.listReplyInfo(autoReplyD);
        Messages messages = null;
        if (infoList != null && infoList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (AutoReplyD vm :
                    infoList) {
                sb.append("《");
                sb.append(vm.getChatKey().trim());
                sb.append("》");
            }
            try {
                messages = Messages.getMessages(Text.of("共" + infoList.size() + "条词条，输入括号内关键字进行查询\n"), Text.of(sb.toString()));

            } catch (Exception e) {
                logger.error("查询构造词条异常", e);
                messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("查询词条异常，请联系管理员！"));
            }
        } else {
            messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("暂无词条！"));
        }
        event.sendBlocking(messages);
    }

    /**
     * 学习词条
     *
     * @param event
     */
    public void studyEntry(MiraiGroupMessageEvent event) {
        // 事件发生的群
        final MiraiGroup groupInfo = event.getGroup();
        final MiraiMember authorInfo = event.getAuthor();

        String msg = event.getMessageContent().getPlainText();
        String[] msgList = msg.split("\\s+");

        Messages messages = null;
        //发言人信息
        logger.info("学习对象key：《{}》，回复key：《{}》", msgList[0], msgList[1]);
//        if (!msgList[1].startsWith(".")) {
//            messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("学习key必须要用.开头！"));
//
//        }   else {
            AutoReplyD autoReplyD = autoReplyService.selectOneByKey(msgList[1]);
            if (autoReplyD != null) {
                messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("已经存在回复，请更新或查询！"));
            } else {

                AutoReplyD vo = new AutoReplyD();
                vo.setReplyId(String.valueOf(groupInfo.getId()));
                vo.setReplyName(groupInfo.getName());

                vo.setInsertUserId(String.valueOf(authorInfo.getId()));
                vo.setInsertUserName(authorInfo.getUsername());
                vo.setChatKey(msgList[1].trim());
                vo.setChatValue(msgList[2].trim());
                vo.setType("1");
                if (autoReplyService.saveInfo(vo)) {
                    messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("学习成功！"));
                } else {
                    messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("学习失败，请联系管理员！"));
                }
//            }
        }
        event.sendBlocking(messages);
    }

    /**
     * 更新词条
     *
     * @param event
     */
    public void updateEntry(MiraiGroupMessageEvent event) {
        // 事件发生的群
        final MiraiGroup groupInfo = event.getGroup();
        final MiraiMember authorInfo = event.getAuthor();

        String msg = event.getMessageContent().getPlainText();
        String[] msgList = msg.split("\\s+");

        Messages messages = null;


        logger.info("更新对象key：《{}》，回复key：《{}》", msgList[0], msgList[1]);
        AutoReplyD autoReplyD = autoReplyService.selectOneByKey(msgList[1]);
        if (autoReplyD == null) {
            messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("不存在该回复，请学习！"));
        } else {
            AutoReplyD vo = new AutoReplyD();
            vo.setId(autoReplyD.getId());
            vo.setReplyId(String.valueOf(groupInfo.getId()));
            vo.setReplyName(groupInfo.getName());
            vo.setUpdateUserId(String.valueOf(authorInfo.getId()));
            vo.setUpdateUserName(authorInfo.getUsername());
            vo.setChatKey(msgList[1].trim());
            vo.setChatValue(msgList[2].trim());
            if (autoReplyService.updateInfo(vo)) {
                messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("更新成功！"));
            } else {
                messages = Messages.getMessages(new At(Identifies.ID(String.valueOf(event.getAuthor().getId()))), Text.of("更新失败，请联系管理员！"));
            }
        }
        event.sendBlocking(messages);
    }

    /**
     * 加密jasypt
     *
     * @param event
     */
    public void encryptByJasypt(MiraiGroupMessageEvent event) {
        String msg = event.getMessageContent().getPlainText();
        String[] msgList = msg.split("\\s+");

        Messages messages = null;

        if (msgList.length < 3) {
            messages = Messages.getMessages(Text.of("jasypt加密失败，样例输入：.加密jasypt key password！"));
            event.sendBlocking(messages);
        }else {
            textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword(msgList[1]);
            String value = null;
            try {
                value = textEncryptor.encrypt(msgList[2]);
            } catch (Exception e) {
                value = "加密失败，请联系管理员！";
            }
            messages = Messages.getMessages(Text.of(value));
        }
        event.replyBlocking(messages);
    }

    /**
     * 解密jasypt
     *
     * @param event
     */
    public void decryptByJasypt(MiraiGroupMessageEvent event) {

        String msg = event.getMessageContent().getPlainText();
        String[] msgList = msg.split("\\s+");

        Messages messages = null;

        if (msgList.length < 3) {
            messages = Messages.getMessages(Text.of("jasypt解密失败，样例输入：.解密jasypt key password！"));
            event.sendBlocking(messages);
        }else {
            textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword(msgList[1]);
            String value = null;
            try {
                value = textEncryptor.decrypt(msgList[2]);
            } catch (Exception e) {
                value = "解密失败，请联系管理员！";
            }
            messages = Messages.getMessages(Text.of(value));
        }
        event.sendBlocking(messages);
    }

    /**
     * 清空所有缓存， 所有缓存都在这里进行统一清空
     */
    public void clearAllCache(){
        //清空回复缓存
        autoReplyService.clearReplyCache();
    }
}