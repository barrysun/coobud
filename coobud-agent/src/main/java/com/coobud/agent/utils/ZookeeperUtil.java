package com.coobud.agent.utils;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperUtil {
	
	public ZooKeeper createZk() throws IOException{	
		ZooKeeper zk=new ZooKeeper("10.16.0.211:2181",500000,new Watcher(){
			//监控所有被触发的事件
			public void process(WatchedEvent event) {
				 System.out.println("已经触发了" + event.getType() + "事件！");
				 System.out.println(event.getPath());
				 System.out.println(event.getState());
			}
		});
		return zk;
	}
	
	public void createNode(ZooKeeper zk,String nodePath,String data) throws KeeperException, InterruptedException{
		zk.create(nodePath, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	public List<String> children(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException{
		 return zk.getChildren(nodePath, true);
	}
	
	public byte[] getData(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException{
		return zk.getData(nodePath, true, null);
	}
	
	
	public void close(ZooKeeper zk) throws InterruptedException{
		zk.close();
	}
	

}
