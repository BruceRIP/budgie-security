package mx.budgie.commons.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author brucerip
 *
 */
public class HashUtil {

	private static final Logger LOGGER = LogManager.getLogger(HashUtil.class);
	
	private HashUtil() {}
	
	public static String gettingHashFromByteArray(byte[] stream) throws NoSuchAlgorithmException {
		LOGGER.info("Creating hash code from byte array");
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		md.update(stream, 0, 1024);
		byte[] mdbytes = md.digest();
		StringBuilder hexString = new StringBuilder();
		for (byte b: mdbytes) {
			hexString.append(String.format("%02X", b));
		}
		LOGGER.info("Hash code was create successfully {}", hexString.toString());
		return hexString.toString();
	}
}
