package com.qiu.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    //设置当前线程的局部变量值
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    //返回当前线程所对应的线程局部变量值
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    //一处当前线程的局部变量值
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
