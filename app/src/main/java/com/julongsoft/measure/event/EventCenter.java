package com.julongsoft.measure.event;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;


import com.julongsoft.measure.global.GlobalApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * <br>
 * 用于 Android 消息的异步解耦合，是线程安全的。
 * <p/>
 * <br>
 * 消息按“topic”分类，Callback 可以订阅感兴趣的 topic。
 * <p/>
 * <br>
 * 对于一个 Callback，可以用 EventCenter.getInstance().subscribe(String, Callback) 来订阅
 * <br>
 * 对于一个消息产生者，可以用 EventCenter.getInstance().post(String, Message) 来发出消息， 也可以用
 * EventCenter.getInstance().broadcast(Message) 来向所有Callback来发出消息
 * <p/>
 * <p/>
 * <font color="red">***注意所有的消息回调都是在UI线程上回调的，在回调里不要做耗时操作***</font>
 */
public class EventCenter {
    private static HashMap<String, HashSet<Callback>> m_CallbackMap = new HashMap<String, HashSet<Callback>>();

    private static HashMap<String, HashSet<Callback>> m_NoUiCallbackMap = new HashMap<String, HashSet<Callback>>();

    /**
     * 让CallBack能在UI线程回调
     */
    private static final Handler handler = new Handler(GlobalApplication.getContext().getMainLooper());

    /**
     * 用于在UI线程上执行
     */
    private static class MessageTask implements Runnable {
        final String topic;
        final Message msg;

        MessageTask(String topic, Message msg) {
            this.topic = topic;
            this.msg = msg;
        }

        @Override
        public void run() {
            if (TextUtils.isEmpty(topic)) {
                // 如果主题为空那么认为是广播
                broadcastInternalUse(msg);
            } else {
                postInternalUse(topic, msg);
            }

        }
    }

    /**
     * 订阅一种主题的消息
     *
     * @param callback The Callback
     * @param topic    主题{@link EventTopic}
     */
    public static synchronized void subscribe(String topic, Callback callback) {
        HashSet<Callback> set = m_CallbackMap.get(topic);
        if (set == null) {
            set = new HashSet<Callback>();
            m_CallbackMap.put(topic, set);
        }
        set.add(callback);
    }

    /**
     * 取消订阅
     *
     * @param topic    主题{@link EventTopic}
     * @param callback The Callback
     */
    public static synchronized void unSubscribe(String topic, Callback callback) {
        HashSet<Callback> set = m_CallbackMap.get(topic);
        if (set != null && set.contains(callback)) {
            set.remove(callback);
        }
    }

    /**
     * 取消该 Callback 订阅的全部主题的消息
     *
     * @param callback The Callback
     */
    public static synchronized void unsubscribeAll(Callback callback) {
        for (HashSet<Callback> set : m_CallbackMap.values()) {
            if (set.contains(callback)) {
                set.remove(callback);
            }
        }
    }

    /**
     * Post 一个特定主题的消息
     *
     * @param topic 主题{@link EventTopic}
     * @param msg   具体的消息{@link Message}
     */
    public static void post(String topic, Message msg) {
        handler.post(new MessageTask(topic, msg));
    }

    /**
     * Post 一个特定主题的消息(内部使用)
     *
     * @param topic 主题{@link EventTopic}
     * @param msg   具体的消息{@link Message}
     */
    private static synchronized void postInternalUse(String topic, Message msg) {
        HashSet<Callback> set = m_CallbackMap.get(topic);
        if (set != null) {
            for (Callback h : set) {
                h.handleMessage(msg);
            }
        }
    }

    /**
     * Post 一个特定主题的消息
     *
     * @param topic 主题{@link EventTopic}
     * @param what  和此topic相关的具体消息类型 {@link EventType}
     * @param obj   对象引用 {@link Object}
     */
    public static void post(String topic, int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        post(topic, msg);
    }

    /**
     * Post 一个特定主题的消息
     *
     * @param topic 主题{@link EventTopic}
     * @param what  和此topic相关的具体消息类型 {@link EventType}
     */
    public static void post(String topic, int what) {
        Message msg = Message.obtain();
        msg.what = what;
        post(topic, msg);
    }

    /**
     * Post 一个特定主题的消息
     *
     * @param topic 主题{@link EventTopic}
     * @param what  和此topic相关的具体消息类型 {@link EventType}
     * @param arg1
     * @param arg2
     * @param obj   对象引用 {@link Object}
     */
    public static void post(String topic, int what, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;

        post(topic, msg);
    }

    /**
     * 广播一个特定主题的消息(内部使用)
     *
     * @param msg The message
     * @return The number of notified Callback, e.g. send messages.
     */
    private static synchronized void broadcastInternalUse(Message msg) {
        for (HashSet<Callback> set : m_CallbackMap.values()) {
            for (Callback h : set) {
                h.handleMessage(msg);
            }
        }
    }

    /**
     * 广播一个特定主题的消息
     *
     * @param msg The message{@link Message}
     * @return The number of notified Callback, e.g. send messages.
     */
    public static void broadcast(Message msg) {
        handler.post(new MessageTask(null, msg));
    }

    /**
     * 广播一个特定主题的消息
     *
     * @param what
     * @param obj
     */
    public static void broadcast(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        broadcast(msg);
    }

    /**
     * 广播一个特定主题的消息
     *
     * @param what
     * @param arg1
     * @param arg2
     * @param obj
     */
    public static void broadcast(int what, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;

        broadcast(msg);
    }

    /**
     * 订阅一种主题的消息
     *
     * @param callback The Callback
     * @param topic    主题{@link EventTopic}
     */
    public static synchronized void subscribeNoUi(String topic, Callback callback) {
        HashSet<Callback> set = m_NoUiCallbackMap.get(topic);
        if (set == null) {
            set = new HashSet<Callback>();
            m_NoUiCallbackMap.put(topic, set);
        }
        set.add(callback);
    }

//	/**
//	 * Post 一个特定主题的消息
//	 *
//	 * @param topic
//	 *            主题{@link EventTopic}
//	 * @param msg
//	 *            具体的消息{@link Message}
//	 */
//	public static void postNoUi(String topic, Message msg)
//	{
//		handler.post(new1 MessageTask(topic, msg));
//		ExecutorUtil.getLocalExecurot().execute(new1 NoticeTask(topic, msg));
//	}

    private static class NoticeTask implements Runnable {

        private final Message message;
        private final String topic;

        NoticeTask(String topic, Message message) {
            this.message = message;
            this.topic = topic;
        }

        @Override
        public void run() {
            noticeNoUi(topic, message);
        }
    }

    private static synchronized void noticeNoUi(String topic, Message message) {
        Set<Callback> sets = m_NoUiCallbackMap.get(topic);
        if (null != sets) {
            for (Callback callback : sets) {
                callback.handleMessage(message);
            }
        }
        message.recycle();
    }
}
