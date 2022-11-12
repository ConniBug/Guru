package com.goru.logger;

public class Log {
	
	private final Severity severity;
	private final String log;
	
	
	public Log(Severity severity, String log) {
		this.severity = severity;
		this.log = log;
	}
	
	
	public Severity getSeverity() {
		return severity;
	}
	public String getLog() {
		return log;
	}
	

}
