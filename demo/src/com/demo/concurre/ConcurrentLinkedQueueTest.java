package com.demo.concurre;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentLinkedQueue��Queue��һ����ȫʵ�֣�Queue��Ԫ�ذ�FIFOԭ��������򣮲���CAS����������֤Ԫ�ص�һ���ԡ�
 * LinkedBlockingQueue��һ���̰߳�ȫ����������
 * ����ʵ����BlockingQueue�ӿڣ�BlockingQueue�ӿڼ̳���java.util
 * .Queue�ӿڣ���������ӿڵĻ�����������take��put�������������������Ƕ��в����������汾��
 * 
 * @author zl
 * 
 */
public class ConcurrentLinkedQueueTest {
	private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
	private static int count = 2; // �̸߳���
	// CountDownLatch��һ��ͬ�������࣬�����һ�����������߳���ִ�еĲ���֮ǰ��������һ�������߳�һֱ�ȴ���
	private static CountDownLatch latch = new CountDownLatch(count);

	public static void main(String[] args) throws InterruptedException {
		long timeStart = System.currentTimeMillis();
		ExecutorService es = Executors.newFixedThreadPool(4);
		ConcurrentLinkedQueueTest.offer();
		for (int i = 0; i < count; i++) {
			es.submit(new Poll());
		}
		latch.await(); // ʹ�����߳�(main)����ֱ��latch.countDown()Ϊ��ż���ִ��
		System.out.println("cost time "
				+ (System.currentTimeMillis() - timeStart) + "ms");
		es.shutdown();
	}

	/**
	 * ����
	 */
	public static void offer() {
		for (int i = 0; i < 100000; i++) {
			queue.offer(i);
		}
	}

	/**
	 * ����
	 * 
	 * @author 
	 * @version 1.0 2013-7-25 ����05:32:56
	 */
	static class Poll implements Runnable {
		public void run() {
			// while (queue.size()>0) {
			while (!queue.isEmpty()) {
				System.out.println(queue.poll());
			}
			latch.countDown();
		}
	}
}