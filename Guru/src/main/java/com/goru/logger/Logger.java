package com.goru.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {

	private static List<Log> logs = new ArrayList<>();
	
	public static void INFO(String log) {
		Log loggedData = new Log(Severity.INFO, log);
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		System.out.println("[" + loggedData.getSeverity().name() + "] [" + stackTraceElements[2].getClassName()+"] " + log);
		logs.add(loggedData);
	}
	public static void WARNING(String log) {
		Log loggedData = new Log(Severity.WARNING, log);
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		System.out.println("[" + loggedData.getSeverity().name() + "] [" + stackTraceElements[2].getClassName()+"] " + log);
		logs.add(loggedData);
	}
	public static void SEVERE(String log) {
		Log loggedData = new Log(Severity.SEVERE, log);
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		System.out.println("[" + loggedData.getSeverity().name() + "] [" + stackTraceElements[2].getClassName()+"] " + log);
		logs.add(loggedData);
	}
	
	
}
