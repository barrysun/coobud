package com.coobud.ssh;

import java.io.File;

import com.jcraft.jsch.Session;

public class FileSenderTest {

	private SSHSession sshSession;
	private FileSender fileSender;

	public void init() {
		sshSession = new SSHSession();
		Session session = sshSession.openSession("root", "123456",
				"10.16.0.110", 22);
		fileSender = new FileSender(session);
		boolean isUploaded = fileSender.upload(
		        "/opt/", new File("E:/akka-demo/akka-demo.iml"));
		session.disconnect();
	}
	
	public static void main(String[] args){
		FileSenderTest fileSenderTest=new FileSenderTest();
		fileSenderTest.init();
	}

}
