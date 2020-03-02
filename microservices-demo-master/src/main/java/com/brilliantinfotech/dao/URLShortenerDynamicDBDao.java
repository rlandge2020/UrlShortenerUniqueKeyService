package com.brilliantinfotech.dao;

import com.brilliantinfotech.utility.UniqueKeyResponse;

/**
 * 
 * @author Rahul Landge
 *
 */
public interface URLShortenerDynamicDBDao {
	/**
	 * 
	 * @return
	 */

	public UniqueKeyResponse getUniqueKey();
	/**
	 * 
	 * @param numberOfKeys
	 * @return
	 */

	public UniqueKeyResponse createKeys(int numberOfKeys);
	
	/**
	 * 
	 * @param key
	 * @return
	 */

	public UniqueKeyResponse deleteKey(String key);

}
