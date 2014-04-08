package io.usoft.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: Sergey Royz
 * Date: 31.03.2014
 */
public class SecurityUtils {

	public static String calculateSHA1(String input) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}