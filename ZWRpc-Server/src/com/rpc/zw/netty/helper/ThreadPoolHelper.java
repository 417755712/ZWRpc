package com.rpc.zw.netty.helper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务线程池初始化类
 * 
 * @author ZZ
 *
 */
public final class ThreadPoolHelper {

	private ThreadPoolHelper() {
	}

	/**
	 * 通用线程池初始化方法,三个属性均不能为空(业务自行保证)
	 * 
	 * @param poolSize
	 * @param queueSize
	 * @param threadName
	 */
	public static ThreadPoolExecutor initGeneralThreadPool(int poolSize, int queueSize, String threadName) {
		ApplicationThreadFactory threadFactory = new ApplicationThreadFactory();
		threadFactory.name = threadName;
		return new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueSize), threadFactory);
	}

	/**
	 * 自定义线程工厂,设置线程名称格式为 name + number(同一业务名称递增)
	 * 
	 * @author ZZ
	 *
	 */
	private static class ApplicationThreadFactory implements ThreadFactory {
		String name;
		int number = 0;

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName(name + "-" + (number++));
			return thread;
		}
	}
}
