package com.spider.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;//通过该框架来控制线程的启动、执行和关闭，可以简化并发编程的操作。

public class ThreadUtil {
	
	// 创建一个可重用固定个数的线程池，避免系统生成过多线程，浪费电脑空间
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);//创建了一个固定大小的线程池,容量是为3
    
    
    public static void setThreadPool(ExecutorService executorService) {
    	fixedThreadPool = executorService;//自定义线程数，homepage传入
    }

    /**
     * 获取线程池
     * @return
     */
    public static ExecutorService getThreadPool() {
    	return fixedThreadPool;
    }
    
    /**
     * 停止所有工作
     */
    public static void stop() {
    	fixedThreadPool.shutdownNow();//当已经提交的任务执行完后，它会将那些闲置的线程（idleWorks）进行中断，这个过程是异步的。
    }
    
	
    
    

}
