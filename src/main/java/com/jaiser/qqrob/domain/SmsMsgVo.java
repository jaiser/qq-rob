package com.jaiser.qqrob.domain;

/**
 * 短信对象
 *
 * @author Jaiser on 2022/3/31
 */
public class SmsMsgVo {

    private String content;

    private long receiverId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "SmsMsgVo{" +
                "content='" + content + '\'' +
                ", receiverId=" + receiverId +
                '}';
    }
}