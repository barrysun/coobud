package com.coobud.ssh;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ShellExecuter {
	
	private static final int BLOCK_SIZE=1024;
	private Session session;
	
	public ShellExecuter(Session session){
		this.session=session;
	}
	
	private ChannelExec openChannelExec(Session session){
		ChannelExec channelExec=null;
		
		try{
			Channel channel = session.openChannel("exec");
			channelExec=(ChannelExec) channel;
		}catch(JSchException e){
			e.printStackTrace();
		}
		return channelExec;
	}
	
	public String execute(String command) throws IOException,JSchException{
		ChannelExec channelExec=openChannelExec(session);
		StringBuffer buffer=executeCommand(command,channelExec);
		//closeChannelExec(channelExec);
		return buffer.toString();
	}
	
	private StringBuffer executeCommand(String command, ChannelExec channelExec)
		      throws IOException, JSchException {
		    InputStream result = channelExec.getInputStream();
		    channelExec.setCommand(command);
		    channelExec.connect();
		    StringBuffer buffer = generateResult(result);
		    return buffer;
		  }

		  private StringBuffer generateResult(InputStream inputStream)
		      throws IOException {
		    StringBuffer buffer = new StringBuffer();
		    byte[] bytes = new byte[BLOCK_SIZE];
		    while (inputStream.read(bytes) > 0) {
		      buffer.append(new String(bytes));
		    }
		    return buffer;
		  }

		  private void closeChannelExec(ChannelExec channelExec) {
		    channelExec.disconnect();
		  }
	

}
