package com.coobud.agent.utils;

import java.util.List;

import org.apache.zookeeper.ZooKeeper;

public class ZookeeperUtilTest2 {
	
	
	public static void main(String[] args) throws Exception{
		ZookeeperUtil zkUtil=new ZookeeperUtil();
		ZooKeeper zk=zkUtil.createZk();
//		zkUtil.createNode(zk, "/barry", "2343");
		zkUtil.createNode(zk, "/barry/root/xxxxx", "root");
		List<String> ls=zkUtil.children(zk, "/barry");
//		for(String str:ls){
//			System.out.println(str);
//		}
		
		//System.out.println(zkUtil.getData(zk, "/hbase-unsecure/running"));
		//zkUtil.close(zk);
	}

}
