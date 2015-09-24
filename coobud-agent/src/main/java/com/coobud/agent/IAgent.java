package com.coobud.agent;

public interface IAgent {
	
	public void start() throws Exception;
	
	public void shutdown() throws Exception;
	
	public void restart() throws Exception;
	
	public void status() throws Exception;

}
