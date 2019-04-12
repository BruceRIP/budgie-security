/**
 * 
 */
package mx.budgie.commons.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mx.budgie.commons.exception.AESCryptException;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
public class AESCrypt {

	private AESCrypt() {
	}

	private static final Logger LOGGER = LogManager.getLogger(AESCrypt.class);
	private static final String ALGORITM = "AES/CBC/PKCS5Padding";
	private static final String UNICODE_FORMAT = "UTF-8";
	private static final String GENERATE_KEY_VALUE = "AES";
	private static byte[] iv = new byte[16];

	public static byte[] encryptToByte(String data, String keyValue) {
		byte[] encVal = null;
		if (data != null) {
			try {
				Key key = generateKey(keyValue);
				Cipher c = Cipher.getInstance(ALGORITM);
				IvParameterSpec ivspec = new IvParameterSpec(iv);
				c.init(1, key, ivspec);
				encVal = c.doFinal(data.getBytes(UNICODE_FORMAT));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return encVal;
	}

	public static String encryptToString(String data, String keyValue) {
		byte[] encVal = encryptToByte(data, keyValue);
		String encryptedValue = null;
		if (encVal != null) {
			byte[] encrypted = Base64.encodeBase64(encVal);
			encryptedValue = new String(encrypted);
		}
		return encryptedValue;
	}

	public static byte[] decryptToByte(String data, String keyValue) {
		byte[] decValue = null;
		if (data != null) {
			try {
				Key key = generateKey(keyValue);
				Cipher c = Cipher.getInstance(ALGORITM);
				IvParameterSpec ivspec = new IvParameterSpec(iv);
				c.init(2, key, ivspec);
				byte[] decodedValue = Base64.decodeBase64(data);
				decValue = c.doFinal(decodedValue);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return decValue;
	}

	public static String decryptToString(String data, String keyValue) throws AESCryptException {
		try {
			byte[] decValue = decryptToByte(data, keyValue);
			String decryptedValue = null;
			if (decValue != null) {
				decryptedValue = new String(decValue);
			}
			return decryptedValue;
		} catch (Exception ex) {
			throw new AESCryptException(ex);
		}
	}

	private static Key generateKey(String keyText) {
		byte[] keyValue = Arrays.copyOf(keyText.getBytes(), 16);
		return new SecretKeySpec(keyValue, GENERATE_KEY_VALUE);
	}

	public static String encryptKeyAndEncodeBase64(String source, String key) {
		String encryptSource = encryptToString(source, key);
		return new String(Base64.encodeBase64(encryptSource.getBytes()));
	}

	public static String decryptKeyAndDecodeBase64(String source, String key) throws AESCryptException {
		try {
			String decodeBase64 = new String(Base64.decodeBase64(source.getBytes()));
			return decryptToString(decodeBase64, key);
		} catch (Exception ex) {
			throw new AESCryptException(ex);
		}
	}

	public static String generateRandomValue() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(50, random).toString(32);
	}

	public static String getUniqueUUID(String key) {
		UUID uniqueKey = null;
		if (key != null) {
			uniqueKey = UUID.nameUUIDFromBytes(key.getBytes());
		} else {
			uniqueKey = UUID.randomUUID();
		}
		return uniqueKey.toString();
	}

	/**
	 * Build a hash value with the SHA 256 Digest Algorithm
	 * 
	 * @param password
	 * @return
	 */
	public static String buildPassword(final String password) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Building hash value from default {} Digest Algorithm", DigestAlgorithms.SHA_256);
		}
		return buildHashValue(password, DigestAlgorithms.SHA_256);
	}

	/**
	 * Build a hash value with a Digest Algorithm type
	 * 
	 * @param password
	 * @param digestAlgorithm
	 * @return
	 */
	public static String buildPassword(final String password, final DigestAlgorithms digestAlgorithm) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Building hash value from {} Digest Algorithm", digestAlgorithm);
		}
		return buildHash(password, digestAlgorithm);
	}

	public static String buildHashValue(final String source) {
		return buildPassword(source);
	}

	public static String buildHashValue(final String source, final DigestAlgorithms digestAlgorithm) {
		return buildPassword(source, digestAlgorithm);
	}

	/**
	 * Default hash value for this source is sha256 hexadecimal
	 * 
	 * @param source
	 * @param digestAlgorithms
	 * @return source hashed
	 */
	protected static String buildHash(final String source, final DigestAlgorithms digestAlgorithms) {
		switch (digestAlgorithms) {
		case MD2:
			return DigestUtils.md2Hex(source);
		case SHA_1:
			return DigestUtils.sha1Hex(source);
		case MD5:
			return DigestUtils.md5Hex(source);
		case SHA_384:
			return DigestUtils.sha384Hex(source);
		case SHA_512:
			return DigestUtils.sha512Hex(source);
		case SHA_256:
			return DigestUtils.sha256Hex(source);
		}
		return null;
	}

}
