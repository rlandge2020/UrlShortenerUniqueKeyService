package com.brilliantinfotech.utility;

import java.security.SecureRandom;

/**
 * This class will generate a unique 8-character alphanumeric key that will be
 * mapped with a long url.
 * 
 * @author Rahul Landge
 *
 */
public class RandomKeyGenerator {

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER + PLUS + MINUS;

	/**
	 * This method returns a random string generated with SecureRandom
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {

		SecureRandom random = new SecureRandom();
		if (length < 1)
			throw new IllegalArgumentException("Length cannot be less than 1");
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			// 0-64 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			System.out.println("Random string:" + generateRandomString(8));

		}
	}

}
