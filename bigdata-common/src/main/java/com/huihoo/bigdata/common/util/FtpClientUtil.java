package com.huihoo.bigdata.common.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpClientUtil {
private Logger logger=Logger.getLogger(FtpClientUtil.class);
	
	private String host;
	private int port;
	private String userName;
	private String password;
	
	private boolean binaryTransfer=true;
	private boolean passiveMode=true;
	private String encoding ="UTF-8";
	private int clientTimeout = 3000;
	private boolean flag=true;
	
	
	
	
	/**
	 * 设置文件传输类型
	 * @param ftpClient
	 * @throws Exception
	 */
	private void setFileType(FTPClient ftpClient) throws Exception{
		try{
			if(binaryTransfer){
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}else {
				ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			}
			
		} catch (IOException e){
			throw new Exception("Could not to set");
		}
		
	}
	
	
	
	private FTPClient getFTPClient() throws Exception{
		FTPClient ftpClient=new FTPClient();//
		ftpClient.setControlEncoding(encoding);
		connect(ftpClient);
		logger.info("ftp 连接成功");
		//设置为passive模式
		if(passiveMode){
			ftpClient.enterLocalPassiveMode();
		}
		setFileType(ftpClient);//设置文件传输类型
		try {
			ftpClient.setSoTimeout(clientTimeout);
		}catch(Exception e){
			throw new Exception("set timeout error");
		}
		return ftpClient;
		
	}
	/**
	 * 连接到ftp服务器
	 * @param ftpClient
	 * @return
	 * @throws Exception
	 */
	public boolean connect(FTPClient ftpClient) throws Exception{
		try{
			ftpClient.connect(host,port);
			//连接后检测返回码来验证链接是否成功
			int reply = ftpClient.getReplyCode();
			if(FTPReply.isPositiveCompletion(reply)) {
				//登录到ftp服务器
				if(FTPReply.isPositiveCompletion(reply)) {
					setFileType(ftpClient);
					return true;
				}
			}else {
				ftpClient.disconnect();
				throw new Exception("");
			}
		}catch (IOException e){
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
					
				}catch(IOException ex){
					throw new Exception("");
				}
			}
		}
		return false;
	}
	
	private void disconnect(FTPClient ftpClient) throws Exception{
		try{
			ftpClient.logout();
			if(ftpClient.isConnected()){
				ftpClient.disconnect();
			}
		}catch(Exception e){
			throw new Exception("Could not disconnection");
		}
	}
	/**
	 * 上传一个本地文件到远程指定文件
	 * @param serverFile
	 * @param localFile
	 * @return
	 */
	public boolean put(String serverFile,String localFile) throws Exception{
		return put(serverFile,localFile,false);
	}
	
	/**
	 * 上传一个本地文件到远程指定文件
	 * @param serverFile
	 * @param localFile
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public boolean put(String serverFile,String localFile,boolean delFile) throws Exception{
		FTPClient ftpClient=null;
		InputStream input=null;
		try{
			ftpClient=getFTPClient();
			//处理传输
			input=new FileInputStream(localFile);
			ftpClient.storeFile(serverFile, input);
			logger.info("put "+localFile);
			input.close();
			if(delFile){
				(new File(localFile)).delete();
			}
			logger.info("delete" +localFile);
			return true;
		}catch(Exception e){
			throw  e;
		}finally{
			try{
				if(input!=null) {
					input.close();
				}
			}catch(Exception ex){
				throw ex;
			}
			if(ftpClient!=null){
				disconnect(ftpClient);//断开连接
			}
		}
	}
	/**
	 * 下载一个远程文件到本地的指定文件
	 * @param serverFile
	 * @param localFile
	 * @return
	 * @throws Exception
	 */
	public boolean get(String serverFile,String localFile) throws Exception{
		return get(serverFile,localFile,false);
	}
	/**
	 * 下载一个远程文件到本地的指定文件
	 * @param serverFile
	 * @param localFile
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public boolean get(String serverFile,String localFile,boolean delFile) throws Exception{
		OutputStream output=null;
		try{
			output=new FileOutputStream(localFile);
			return get(serverFile,output,delFile);
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(output!=null){
					output.close();
				}
			}catch(Exception ex){
				throw ex;
			}
		}
	}
	
	/**
	 * 下载一个远程文件到指定的流处理完后记得关闭流
	 * @param serverFile
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public boolean get(String serverFile,OutputStream output) throws Exception{
		return get(serverFile,output,false);
	}
	
	public boolean get(String serverFile,OutputStream output,boolean delFile)throws Exception{
		FTPClient ftpClient=null;
		try{
			ftpClient=getFTPClient();
			//处理传输
			ftpClient.retrieveFile(serverFile, output);
			if(delFile){
				ftpClient.deleteFile(serverFile);
			}
			return true;
		}catch(Exception e){
			throw  e;
		}finally{
			if(ftpClient!=null){
				disconnect(ftpClient);//断开连接
			}
		}
	}
	
	/**
	 * 从ftp服务器上删除一个文件
	 * @param delFile
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String delFile) throws Exception{
		FTPClient ftpClient=null;
		try{
			ftpClient=getFTPClient();
			ftpClient.deleteFile(delFile);
			return true;
		}catch(IOException e){
			throw e;
		}finally{
			if(ftpClient!=null){
				disconnect(ftpClient);//
			}
		}
	}
	/**
	 * 批量删除
	 * @param delFiles
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String[] delFiles) throws Exception{
		FTPClient ftpClient=null;
		try{
			ftpClient=getFTPClient();
			for(String s:delFiles){
				ftpClient.deleteFile(s);
			}
			return true;
		}catch(Exception e){
			throw e;
		}finally{
			if(ftpClient!=null){
				disconnect(ftpClient);//断开连接
			}
		}
		
	}
	/**
	 * 列出远程默认目录下所有的文件
	 * @return
	 * @throws Exception
	 */
	public String[] listNames() throws Exception{
		return listNames(null);
	}
	/**
	 * 列出远程目录下所有的文件
	 * @param remotePath
	 * @return
	 * @throws Exception
	 */
	public String[] listNames(String remotePath) throws Exception{
		FTPClient ftpClient=null;
		try{
			ftpClient=getFTPClient();
			String[] listNames=ftpClient.listNames(remotePath);
			return listNames;
		}catch(Exception e){
			throw e;
		}finally{
			if(ftpClient!=null){
				disconnect(ftpClient);//断开连接
			}
		}
	}
	
	public boolean isExist(String remoteFilePath) throws Exception{
		FTPClient ftpClient=null;
		try{
			ftpClient=getFTPClient();
			File file=new File(remoteFilePath);
			
			String remotePath=remoteFilePath.substring(0,remoteFilePath.indexOf(file.getName())-1);
			
			String[] listNames=ftpClient.listNames(remotePath);
			System.out.println(remoteFilePath);
			for(int i=0;i<listNames.length;i++){
				if(remoteFilePath.equals(listNames[i])){
					flag=true;
					System.out.println("文件："+file.getName()+"文件已经存在了");
					break;
				}else {
					flag=false;
				}
			}
		}catch(IOException e){
			throw e;
		}finally{
			if(ftpClient!=null){
				disconnect(ftpClient);//断开连接
			}
		}
		
		return flag;
	}
}
