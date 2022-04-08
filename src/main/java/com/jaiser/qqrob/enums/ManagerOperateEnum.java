package com.jaiser.qqrob.enums;

/**
 * 管理员操作枚举
 *
 * @author Jaiser on 2022/4/3
 */
public enum ManagerOperateEnum {

    OPERATE_STATE("1","查看所有操作状态"),
    OPERATE_FRIEND_START("2","开启好友监听功能"),
    OPERATE_FRIEND_STOP("3","关闭好友监听功能"),
    OPERATE_GROUP_START("4","开启群组监听功能"),
    OPERATE_GROUP_STOP("5","关闭群组监听功能"),
    OPERATE_CLEAR_CACHE("99","清空所有缓存"),
    OPERATE_EXIT("0","退出操作")
    ;

    ManagerOperateEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;

    private String desc;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    // 根据value获取枚举
    public static ManagerOperateEnum getEnumByValue(String value) {
        for (ManagerOperateEnum managerOperateEnum : ManagerOperateEnum.values()) {
            if (managerOperateEnum.getValue().equals(value)) {
                return managerOperateEnum;
            }
        }
        return null;
    }

}