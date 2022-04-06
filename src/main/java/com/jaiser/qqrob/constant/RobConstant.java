package com.jaiser.qqrob.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 机器人配置
 *
 * @author Jaiser on 2022/4/3
 */
@Component
public class RobConstant {

//    private static RobConstant robConstant = new RobConstant();
//
//    /**
//     * 单例获取
//     * @return
//     */
//    public static RobConstant INSTANCE() {
//        if (robConstant == null) {
//            robConstant = new RobConstant();
//        }
//        return robConstant;
//    }

    // 默认管理员账号
    @Value("${rob.manager}")
    private String manager;
    // 默认群组开启监听
    private Boolean groupEnable = true;
    // 默认私聊不开启监听
    private Boolean friendEnable = false;


    /**
     * 获取管理员号码
     * @return
     */
    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setGroupEnable(Boolean groupEnable) {
        this.groupEnable = groupEnable;
    }

    public void setFriendEnable(Boolean friendEnable) {
        this.friendEnable = friendEnable;
    }

    /**
     * 是否开启群组监控
     * @return
     */
    public Boolean isGroupEnable() {
        return this.groupEnable;
    }

    /**
     * 是否开启私聊监控
     * @return
     */
    public Boolean isFriendEnable() {
        return this.friendEnable;
    }

    /**
     * 是否是管理员账号
     * @param qq
     * @return
     */
    public Boolean isManager(String qq) {
        String[] managerList = this.manager.split(",");
        for (String manager:
             managerList) {
            if (Objects.equals(qq, manager)) {
                return true;
            }
            return false;
        }
        return false;
    }

}