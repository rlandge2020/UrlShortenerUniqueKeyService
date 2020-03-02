package com.brilliantinfotech.utility;

/**
 * Response object for the KeyGeneraion Service.
 * 
 * @author Rahul Landge
 *
 */
public class UniqueKeyResponse {

	private String key;

	private String statusCode;

	private String statusMessage;

	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
