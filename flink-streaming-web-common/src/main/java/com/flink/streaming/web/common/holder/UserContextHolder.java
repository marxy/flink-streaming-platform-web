package com.flink.streaming.web.common.holder;


import com.flink.streaming.web.model.dto.UserSession;

/**
 * 用户会话线程变量
 *
 * @Author: quentin.zeng
 * @Date: 2022/5/19 18:59
 */
public class UserContextHolder {

    private static final ThreadLocal<UserSession> userSessionHolder = new ThreadLocal<>();

    public static UserSession get() {
        return userSessionHolder.get();
    }

    public static void set(UserSession userSession) {
        userSessionHolder.set(userSession);
    }

    public static void remove() {
        userSessionHolder.remove();
    }
}
