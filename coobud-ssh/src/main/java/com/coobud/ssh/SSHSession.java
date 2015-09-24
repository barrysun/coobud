package com.coobud.ssh;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//http://www.tuicool.com/articles/vMfYRr

public class SSHSession {
	
	
	private static final int TIMEOUT=3000;
	
	public Session openSession(String userName,String pwd,String host,int port){
		Session session=null;
		try{
			session=connect(userName,pwd,host,port);
		}catch(JSchException e){
			 System.out.println("[ERROR] " + e.getMessage()
			          + ", check your username and password.");
		}
		return session;
	}
	
	private Session connect(String userName,String pwd,String host,int port) throws JSchException{
		Session session = new JSch().getSession(userName, host, port);
	    session = skipHostKeyChecking(session);
	    session.setPassword(pwd);
	    session.connect(TIMEOUT);
	    return session;
	}
	
	 private Session skipHostKeyChecking(Session session) {
		    Properties config = new Properties();
		    config.put("StrictHostKeyChecking", "no");
		    session.setConfig(config);
		    return session;
		  }

}
