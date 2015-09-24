package com.coobud.common.cmd;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class SysCmd {

	public static void main(String[] args) throws IOException {

		try {

		//	String command = "ping " + "10.16.0.211";
			String command ="ipconfig";
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			CommandLine commandline = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setExitValues(null);
			PumpStreamHandler streamHandler = new PumpStreamHandler(
					outputStream, errorStream);
			exec.setStreamHandler(streamHandler);
			exec.execute(commandline);
			String out = outputStream.toString("gbk");
			String error = errorStream.toString("gbk");
			System.out.println( out + error);
		} catch (Exception e) {
			//log.error("ping task failed.", e);
			//return e.toString();
		}
	}

}
