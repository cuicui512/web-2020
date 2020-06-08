package com.example.one.exception;

public enum CustomizeErrorCode implements  InCustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你请求的资源已迷失在网络中，请稍后在试试！！！"),
    TARGET_PARM_NOT_FOUND(2002,"未选中任何发布或评论进行回复！"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录"),
    SYS_ERROR(2004,"系统错误！"),
    TYPE_PARM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_EXIST(2006,"你回复的评论已不存在，评论别的试试"),
    CONTENT_IS_EMPTY(2007,"您的评论内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008,"别人的信息不可读哦！！"),
    NOTIFICATION_NOT_FOUND(2009,"回复的消息迷失在网络中，助手正在寻找！！"),
    MORE_CLICK(2010,"不能重复点赞！！");
    private Integer code;
    private String message;
    CustomizeErrorCode(Integer code,String message) {
        this.code=code;
        this. message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}