package com.coobud.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileSender {
	
	private Session session;
	
	public FileSender(Session session){
		this.session=session;
	}
	
	public ChannelSftp openChannelSftp(Session session){
		ChannelSftp channelSftp=null;
		try{
			Channel channel = session.openChannel("sftp");
			channel.connect();
			channelSftp=(ChannelSftp)channel;
			
		}catch(JSchException e){
			e.printStackTrace();
		}
		return channelSftp;
	}
	
	public boolean isChangeedDirectory(String path,ChannelSftp channelSftp){
		try{
			channelSftp.cd(path);
			return true;
		}catch(Exception e){
			System.out.println("[ERROR] " + path + ": No such directory");
		}
		return false;
	}
	
	public boolean upload(String directory,File uploadFile){
		boolean isUploaded = false;
		ChannelSftp channelSftp=openChannelSftp(session);
		if(isChangeedDirectory(directory,channelSftp)){
			isUploaded=uploadFile(directory,uploadFile,channelSftp);
		}
		return isUploaded;
		
	}
	
	 private boolean uploadFile(String directory, File uploadFile,
		      ChannelSftp channelSftp) {
		    boolean isUploadedFile = false;
		    try {
		      channelSftp.put(new FileInputStream(uploadFile),
		          uploadFile.getName());
		      isUploadedFile = isFileUploaded(directory, uploadFile, channelSftp);
		    } catch (Exception e) {
		      System.out.println("[ERROR] " + e.getMessage());
		    }
		    return isUploadedFile;
		  }

		  @SuppressWarnings("unchecked")
		  private boolean isFileUploaded(String directory, File uploadFile,
		      ChannelSftp channelSftp) throws SftpException {
		    boolean isFileUploaded = false;
		    Vector<LsEntry> files = channelSftp.ls(directory);
		    for (LsEntry file : files) {
		      isFileUploaded |= isFileExist(uploadFile, file);
		    }
		    return isFileUploaded;
		  }

		  private boolean isFileExist(File uploadFile, LsEntry file) {
		    return uploadFile.getName().equals(file.getFilename()) ? true : false;
		  }

}
