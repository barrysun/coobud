package com.coobud.common.thread.factory;

import java.util.concurrent.ThreadFactory;

public class MaxPriorityThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable run) {
		Thread t=new Thread(run);
		t.setPriority(Thread.MAX_PRIORITY);
		return t;
	}

}
