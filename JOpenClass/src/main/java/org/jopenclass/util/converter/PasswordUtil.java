/**
 * 
 */
package org.jopenclass.util.converter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Pathmasri Ambegoda
 * 2014Feb 23, 20142:09:59 AM
 */
public class PasswordUtil {
	
	
	public static String getEncryptedPassword(String plainString) throws NoSuchAlgorithmException{
		
		String encryptedPassword = "";
		
		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] passwordByte = plainString.getBytes();
		md.update(passwordByte);
		byte[] digest = md.digest();

		for (int i = 0; i < digest.length; i++) {
			encryptedPassword += Integer.toString((digest[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		return encryptedPassword;
	}
	

}
