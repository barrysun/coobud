package com.coobud.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coobud.common.thread.factory.DaemonThreadFactory;
import com.coobud.common.thread.factory.MaxPriorityThreadFactory;
import com.coobud.common.thread.factory.MinPriorityThreadFactory;

public class ExecFromFactory {
	
	public static void main(String[] args) throws Exception {
		ExecutorService defaultExec = Executors.newCachedThreadPool();
		ExecutorService daemonExec = Executors
				.newCachedThreadPool(new DaemonThreadFactory());
		ExecutorService maxPriorityExec = Executors
				.newCachedThreadPool(new MaxPriorityThreadFactory());
		ExecutorService minPriorityExec = Executors
				.newCachedThreadPool(new MinPriorityThreadFactory());
		for (int i = 0; i < 10; i++)
			daemonExec.execute(new MyThread(i));
		for (int i = 10; i < 20; i++)
			if (i == 10)
				maxPriorityExec.execute(new MyThread(i));
			else if (i == 11)
				minPriorityExec.execute(new MyThread(i));
			else
				defaultExec.execute(new MyThread(i));
	}

}

