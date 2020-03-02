
package com.brilliantinfotech.service;

import com.brilliantinfotech.utility.UniqueKeyResponse;

/**
 * 
 * @author Rahul Landge
 *
 */
public interface KeyGenerationRestService {

	public UniqueKeyResponse getUniqueKey() ;
	
	public UniqueKeyResponse deleteKey(String key) ;
	
	public UniqueKeyResponse createKeys(int numberOfKeys);

}