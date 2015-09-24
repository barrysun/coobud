package com.coobud.common.thread.factory;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable run) {
		Thread thread=new Thread(run);
		thread.setDaemon(true);
		return thread;
	}

}
