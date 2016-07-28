package eg.com.etisalat.contest.utility;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtility {

	public static final String GENERAL_KEY = "8IgmdaO0udAGa*PJ";
	public static final String SECRET_KEY = "8C;HpQmh";

	public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
		SecretKey key = KeyGenerator.getInstance("AES").generateKey();
		return key;
	}

	public static SecretKey generateSecretKey(String keyBase) throws NoSuchAlgorithmException {
		byte key[] = keyBase.getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		return secretKey;
	}

	private final Cipher ecipher;

	private final Cipher dcipher;
	
	private final String keyString;

	public EncryptionUtility(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		keyString =new String(key.getEncoded());
		ecipher = Cipher.getInstance(key.getAlgorithm());
		dcipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);

	}

	public EncryptionUtility(String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		this(new SecretKeySpec(key.getBytes(), "AES"));
	}

	public String decrypt(String str) throws BadPaddingException, IllegalBlockSizeException, IOException {
		// Decode base64 to get bytes
		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
		byte[] utf8 = null;
		// Decrypt
		try {
			utf8 = dcipher.doFinal(dec);
		} catch (Exception e) {
			utf8 = decrypt(dec);
		}
		// Decode using utf-8
		return new String(utf8, "UTF8");

	}

	public String encrypt(String str) throws BadPaddingException, IllegalBlockSizeException, IOException {

		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");

		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);

		// Encode bytes to base64 to get a string
		return new sun.misc.BASE64Encoder().encode(enc);

	}

	private byte[] decrypt(byte[] data) {
		try {
			String key = keyString;
			Key secretkey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher ciph = Cipher.getInstance("AES");
			ciph.init(Cipher.DECRYPT_MODE, secretkey);
			return ciph.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DES decryption error");
		}
	}

}
