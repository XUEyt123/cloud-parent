package org.example.cloudcommon.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolSingle {
    //单例模式-IoDH实现
    public ThreadPoolExecutor poolExecutor;

    private ThreadPoolSingle() {
        //七大参数： 后两个是使用默认的线程工厂和拒绝策略
        poolExecutor = new ThreadPoolExecutor(4, 20, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20));
    }

    private static class PoolSingle {
        private static ThreadPoolSingle signle = new ThreadPoolSingle();
    }

    public static ThreadPoolSingle getInstance() {
        return PoolSingle.signle;
    }

}
