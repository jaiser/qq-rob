package com.jaiser.qqrob.controller;

import com.jaiser.qqrob.utils.BotUtil;
import com.jaiser.qqrob.domain.SmsMsgVo;
import love.forte.simbot.Identifies;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 转发接口
 *
 * @author Jaiser on 2022/3/31
 */
@RestController
@RequestMapping("/forward")
public class ForwardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BotUtil botUtil;

    @PostMapping("/sendMsg/{type}/{key}")
    public String sendMsg(@PathVariable("type") Integer type, @PathVariable("key") String key, @RequestBody SmsMsgVo vo) {
        log.info("发送类型:《{}》,发送内容为:《{}》", type, vo);
        if (vo == null || vo.getContent() == null || vo.getReceiverId() <= 0) {
            return "操作异常";
        }
        Message message = Messages.getMessages(Text.of(vo.getContent()));
        if (type == 1) {
            botUtil.INSTANCE().getGroup(Identifies.ID(vo.getReceiverId())).sendBlocking(message);
        }else {
            botUtil.INSTANCE().getFriend(Identifies.ID(vo.getReceiverId())).sendBlocking(message);
        }
        return "操作成功";
    }
}