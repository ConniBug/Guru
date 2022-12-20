package com.syntex.modals;

/**
 * 
 * Immutable class which represents a holiday
 * @author synte
 *
 */
public final class Holiday {

	private final String startDate;
	private final String endDate;
	
	private String name;

	/**
	 * 
	 * @param startDate the start date for this holiday
	 * @param endDate the end date for this holiday
	 * @param name the name for this holiday
	 */
	public Holiday(String startDate, String endDate, String name) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
