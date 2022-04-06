package com.jaiser.qqrob.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.jaiser.qqrob.domain.AutoReplyD;
import com.jaiser.qqrob.mapper.AutoReplyMapper;
import com.jaiser.qqrob.service.AutoReplyService;
import love.forte.di.annotation.Beans;
import love.forte.di.annotation.Depend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jaiser
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AutoReplyServiceImpl implements AutoReplyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AutoReplyMapper autoReplyMapper;

    @Override
    public AutoReplyD selectOneByKey(String chatKey) {
        if (StringUtils.checkValNull(chatKey)) {
            logger.error("回复key为空，无法查询回复信息,查询对象为：《chatKey:{}》", chatKey);
            return null;
        }
        AutoReplyD autoReplyD = new AutoReplyD();
        autoReplyD.setChatKey(chatKey);
        return autoReplyMapper.selectOne(autoReplyD);
    }

    @Override
    public Boolean updateInfo(AutoReplyD autoReplyD) {
        autoReplyD.setUpdateTime(dateFormat.format(new Date()));
        return autoReplyMapper.updateById(autoReplyD) > 0;
    }

    @Override
    public Boolean saveInfo(AutoReplyD autoReplyD) {
        autoReplyD.setInsertTime(dateFormat.format(new Date()));
        return autoReplyMapper.insert(autoReplyD) > 0;
    }

    @Override
    public List<AutoReplyD> listReplyInfo(AutoReplyD autoReplyD) {
        EntityWrapper<AutoReplyD> wrapper = new EntityWrapper<>();
//        if (autoReplyD != null ) {
//            if (autoReplyD.getReplyId() != null) {
//                wrapper.eq("REPLY_ID", autoReplyD.getReplyId());
//            }
//        }
        return autoReplyMapper.selectList(wrapper);
    }

}
