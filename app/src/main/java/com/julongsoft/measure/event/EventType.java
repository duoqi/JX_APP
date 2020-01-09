package com.julongsoft.measure.event;

/**
 * 配合{@link EventCenter}使用，具体的作用是规定每个topic下的事件定义。
 *
 * @author zhao
 */
public class EventType {
    // 900万当起始点
    private static final int BASE_TYPE = 9000000;

    public static class WaitType {

        private static final int BASE = BASE_TYPE + 50000;

        //获取代办消息
        public static final int WHAT_GET_WAIT = BASE + 12;
        //获取添加检查单消息
        public static final int WHAT_ADD_CHECK = BASE + 13;
        //获取删除检查单消息
        public static final int WHAT_DELETE_CHECK = BASE + 14;
        //获取发送检查单消息
        public static final int WHAT_SEND_CHECK = BASE + 15;
        //获取添加回执单消息
        public static final int WHAT_ADD_RECEIPT = BASE + 16;
         //获取发送回执单消息
        public static final int WHAT_SEND_RECEIPT = BASE + 17;
        //获取代办回复消息
        public static final int WHAT_RETURN_WAIT = BASE + 18;


    }


}