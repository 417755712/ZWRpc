package com.rpc.zw.netty.helper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ҵ���̳߳س�ʼ����
 * 
 * @author ZZ
 *
 */
public final class ThreadPoolHelper {

	private ThreadPoolHelper() {
	}

	/**
	 * ͨ���̳߳س�ʼ������,�������Ծ�����Ϊ��(ҵ�����б�֤)
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
	 * �Զ����̹߳���,�����߳����Ƹ�ʽΪ name + number(ͬһҵ�����Ƶ���)
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
