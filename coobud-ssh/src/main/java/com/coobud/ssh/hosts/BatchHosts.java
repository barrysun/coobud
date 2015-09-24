package com.coobud.ssh.hosts;

import java.io.File;
import java.io.IOException;

import com.coobud.ssh.FileSender;
import com.coobud.ssh.SSHSession;
import com.coobud.ssh.ShellExecuter;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 批量配置hosts文件
 * 只支持Linux
 * 
 * @author Administrator
 * 
 * Des:
 * 1.备份hosts文件
 * 2.上传整理好的hosts文件
 * 3.将添加的文件存入hosts内
 * 4.删除上传的文件
 *
 */
public class BatchHosts {
	
	private static final String HOSTS_PATH="/etc/hosts";
	private static long time=System.currentTimeMillis();
	
	public static void main(String[] args) throws IOException, JSchException, InterruptedException{
		BatchHosts batch=new BatchHosts();
		batch.run();
	}
	
	private void run () throws IOException, JSchException, InterruptedException{
		String[] hostArray={
				"10.16.0.211"
				/*"10.16.0.212",
				"10.16.0.213",
				"10.16.0.214",
				"10.16.0.215",
				"10.16.0.216",
				"10.16.0.110",
				"10.16.41.200"*/
				};
		String userName="root";
		String pwd="123456";
		for(String ip:hostArray){
			SSHSession sshSession = new SSHSession();
			Session session = sshSession.openSession(userName, pwd,
					ip, 22);
			catHosts(session);
		}
	}
	
	private void hostsChange(Session session,String uploadPath,String uploadFileName,String localPath) throws IOException, JSchException{
		FileSender fileSender;
		fileSender = new FileSender(session);
		boolean isUploaded = fileSender.upload(uploadPath, new File(localPath));
		ShellExecuter shellExecuter=new ShellExecuter( session);
		shellExecuter.execute("cp /etc/hosts /etc/hosts."+time+".bak");
		shellExecuter.execute("cat "+uploadPath+uploadFileName+" >> /etc/hosts ");
		shellExecuter.execute("rm "+uploadPath+uploadFileName);
		session.disconnect();
	}
	
	private void catHosts(Session session) throws IOException, JSchException, InterruptedException{
		ShellExecuter shellExecuter=new ShellExecuter( session);
		//String str=shellExecuter.execute("jps -mlvV");
		String str2=shellExecuter.execute("cat /tmp/t.txt");
        //	Thread.sleep(1000);
        System.out.println(str2);
		session.disconnect();
	}
	
	private void rollback(Session session,String backFile) throws IOException, JSchException{
		ShellExecuter shellExecuter=new ShellExecuter( session);
		String cpResult =shellExecuter.execute("cp "+backFile+" /etc/hosts ");
		shellExecuter.execute("rm /opt/myhosts");
		session.disconnect();
	}

	
	
	
	
	
	

}
