package com.syntex.modals;

public class Holiday {

	private String startDate;
	private String endDate;
	
	private String name;

	public Holiday(String startDate, String endDate, String name) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
