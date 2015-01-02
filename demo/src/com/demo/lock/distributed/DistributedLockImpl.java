package com.demo.lock.distributed;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DistributedLockImpl extends UnicastRemoteObject implements
		DistributedLock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 超时单位
	 */
	private TimeUnit lockTimeoutUnit = TimeUnit.SECONDS;
	/**
	 * 锁的令牌
	 */
	private volatile long token = 0;
	/**
	 * 同步对象
	 */
	byte[] lock = new byte[0];
	/**
	 * 默认永不超时
	 */
	long lockTimeout = 60 * 60;// 默认超时3600秒
	long beginLockTime;// 获取令牌时间，单位毫秒

	protected DistributedLockImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 * @param lockTimeout
	 *            锁超时时间，如果加锁的对象不解锁，超时之后自动解锁
	 * @param lockTimeoutUnit
	 * @throws RemoteException
	 */
	public DistributedLockImpl(long lockTimeout, TimeUnit lockTimeoutUnit)
			throws RemoteException {
		super();
		this.lockTimeout = lockTimeout;
		this.lockTimeoutUnit = lockTimeoutUnit;
	}

	@Override
	public long lock() throws RemoteException, TimeoutException {
		return tryLock(0, TimeUnit.MICROSECONDS);
	}

	private boolean isLockTimeout() {
		if (lockTimeout <= 0) {
			return false;
		}
		return (System.currentTimeMillis() - beginLockTime) < lockTimeoutUnit
				.toMillis(lockTimeout);
	}

	private long getToken() {
		beginLockTime = System.currentTimeMillis();
		token = System.nanoTime();
		return token;
	}

	@Override
	public long tryLock(long time, TimeUnit unit) throws TimeoutException,
			TimeoutException {
		synchronized (lock) {
			long startTime = System.nanoTime();
			while (token != 0 && isLockTimeout()) {

				try {
					if (time > 0) {
						long endTime = System.nanoTime();
						if (endTime - startTime >= unit.toMillis(time)) {
							throw new TimeoutException();
						}
					}
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return getToken();
		}
	}

	@Override
	public void unlock(long token) throws RemoteException {
		if (this.token != 0 && token == this.token) {
			this.token = 0;
		} else {
			throw new RuntimeException("令牌" + token + "无效.");
		}

	}

}
