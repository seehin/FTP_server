package com.seehin.ftpServer.sever;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Seehin
 * @Date 2020/6/28 00:59
 */
public class ThreadServer {

    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadServer.class) {
                if (mThreadPool == null) {
                    // 获取cpu数量
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    System.out.println("cpu个数: " + cpuCount);
                    int threadCount = 10;
                    mThreadPool = new ThreadPool(threadCount, threadCount, 0L);
                }
            }
        }

        return mThreadPool;
    }

    /**
     * 线程池
     */
    public static class ThreadPool {

        private int corePoolSize;// 核心线程数
        private int maximumPoolSize;// 最大线程数
        private long keepAliveTime;// 休息时间

        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize, int maximumPoolSize,
                           long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable r) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        Executors.defaultThreadFactory(), new AbortPolicy());
                // 参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
            }
            executor.execute(r);
        }

        /**
         * 取消任务
         * @param r 任务
         */
        public void cancel(Runnable r) {
            if (executor != null) {
                // 从线程队列中移除对象
                executor.getQueue().remove(r);
            }
        }

    }

}
