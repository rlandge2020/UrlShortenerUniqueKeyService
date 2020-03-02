package com.brilliantinfotech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.brilliantinfotech.dao.URLShortenerDynamicDBDao;
import com.brilliantinfotech.service.KeyGenerationRestService;
import com.brilliantinfotech.utility.UniqueKeyResponse;

/**
 * 
 * @author Rahul Landge
 *
 */
@Service
@ComponentScan("com.brilliantinfotech.dao.impl")
public class KeyGenerationRestServiceImpl implements KeyGenerationRestService {

	@Autowired
	private URLShortenerDynamicDBDao urlShortenerDynamicDBDao;

	@Override
	public UniqueKeyResponse deleteKey(String key) {
		UniqueKeyResponse uniqueKey = urlShortenerDynamicDBDao.deleteKey(key);
		return uniqueKey;
	}

	@Override
	public UniqueKeyResponse createKeys(int numberOfKeys) {
		UniqueKeyResponse uniqueKey = urlShortenerDynamicDBDao.createKeys(numberOfKeys);
		return uniqueKey;

	}

	@Override
	public UniqueKeyResponse getUniqueKey() {
		UniqueKeyResponse key = urlShortenerDynamicDBDao.getUniqueKey();
		return key;
	}

}