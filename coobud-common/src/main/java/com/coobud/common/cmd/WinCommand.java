package com.coobud.common.cmd;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WinCommand implements ICommand{

	private static Logger LOG = LoggerFactory.getLogger(WinCommand.class);
	
	@Override
	public String exec(String command) throws Exception {
		try {
			//	String command = "ping " + "10.16.0.211";
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
				return out + error;
			} catch (Exception e) {
				LOG.error("ping task failed.", e);
				return e.toString();
			}
	}

}
