package com.fourfoureight.lolhelper.api.dto.Team;

public class MessageOfDay {
	private long createDate;	
	private String message;	
	private int version;
	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
}