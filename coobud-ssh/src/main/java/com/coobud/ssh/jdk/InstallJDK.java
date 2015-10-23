package com.coobud.ssh.jdk;

import java.io.File;
import java.io.IOException;

import com.coobud.ssh.FileSender;
import com.coobud.ssh.SSHSession;
import com.coobud.ssh.ShellExecuter;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 安装JDK
 * 
 * 0.检查是否安装的JDK 1.备份/etc/profile 文件 2.上传JDK 到指定的目录下面 3.解压JDK 到指定目录下面
 * 4.配置/etc/profile 文件 5.验证JDK是否安装成功。 6.删除JDK上传的文件。
 * 
 * @author Administrator
 *
 */

public class InstallJDK {

	public void install(String userName, String pwd, String host, Integer port,
			String directory, String localFile, long time) throws IOException,
			JSchException {
		SSHSession sshSession = new SSHSession();
		Session session = sshSession.openSession(userName, pwd, host, port);
		FileSender fileSender = new FileSender(session);
		boolean isUploaded = fileSender.upload(directory, new File(localFile));
		if (isUploaded) {
			ShellExecuter shellExecuter = new ShellExecuter(session);
			shellExecuter.execute(" tar -zxvf " + directory);
			shellExecuter.execute(" cp /etc/profile /etc/profile" + time
					+ ".bak");
			shellExecuter.execute(" source /etc/profile ");
			session.disconnect();
		}
	}
}
