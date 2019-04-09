/**
 * 
 */
package mx.budgie.billers.accounts.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
public class TokenSms {

	public static String nextSessionId() throws NoSuchAlgorithmException {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		int i = generator.nextInt(1000000) % 1000000;
		java.text.DecimalFormat f = new java.text.DecimalFormat("000000");
		return f.format(i);
	}

	public static String nextTokenView(int len) {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}
