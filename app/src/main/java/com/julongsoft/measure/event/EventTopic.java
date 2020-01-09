package com.julongsoft.measure.event;

/**
 * 配合{@link EventCenter}使用，具体的作用定义topic
 *
 * @author zhao
 */
public class EventTopic {


    /**
     * 代办的定义
     */
    public static final String WAIT_TOPIC = "__WAIT_TOPIC__";
    /**
     * 添加检查单的定义
     */
    public static final String ADD_CHECK = "__ADD_CHECK__";

    /**
     * 添加回执单的定义
     */
    public static final String ADD_RECEIPT = "__ADD_RECEIPT__";
    /**
     * 删除检查单的定义
     */
    public static final String DELETE_CHECK = "__DELETE_CHECK__";

    /**
     * 发送检查单的定义
     */
    public static final String SEND_CHECK_LIST = "__SEND_CHECK_LIST__";

    /**
     * 发送回执单的定义
     */
    public static final String SEND_RECEIPT_LIST = "__SEND_RECEIPT_LIST__";

    /**
     * 代办回复消息的定义
     */
    public static final String RETURN_WAIT_LIST = "__RETURN_WAIT_LIST__";

}