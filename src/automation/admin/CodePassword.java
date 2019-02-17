package automation.admin;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CodePassword {

    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;
	
	public CodePassword() {
	
	}
	
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
//        String encStr = Base64.encodeBase64String();
        return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
    }	
    
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndHash = stored.split("\\$");
        if (saltAndHash.length != 2) {
            throw new IllegalStateException(
                "The stored password must have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.getDecoder().decode(saltAndHash[0]));
/*        
        System.out.println("hasło z bazy: "+stored);
        System.out.println("hasło z formularza: "+password);
        
        System.out.println("salt = "+saltAndHash[0]);
        System.out.println("hash = "+saltAndHash[1]);
        
*/
        char[] t1 = saltAndHash[1].toCharArray();
        char[] t2 = hashOfInput.toCharArray();
        boolean isEqual = true;
        
        for (int i=0;i<hashOfInput.length();i++) {
        	if (t1[i] != t2[i]) {
        		isEqual = false;
                System.out.println(saltAndHash[1]);
                System.out.println(hashOfInput);
        		break;
        	}
        }
          return isEqual;
        
//        return hashOfInput.equals(saltAndHash[1]);
    }


    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        SecretKey key = f.generateSecret(new PBEKeySpec(
            password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

	
/*	
	private String[] codePassword(String password, byte[] salt ) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = f.generateSecret(spec).getEncoded();		
		Base64.Encoder enc = Base64.getEncoder();
		//System.out.printf("salt: %s%n", enc.encodeToString(salt));
		//System.out.printf("hash: %s%n", enc.encodeToString(hash));
		String[] saltHash = {enc.encodeToString(salt),
							 enc.encodeToString(hash)};
		return saltHash;
	}
	
	public byte[] generateRandomSalt() {
		
		Random rand = new Random(System.currentTimeMillis());
		byte[] salt = new byte[32];
		rand.nextBytes(salt);
	
		return salt;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean checkPasswordCorrect(String passFromForm, String dbPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		String saltString = dbPassword.substring(0, 44);
		
		byte[] salt = dbPassword.substring(0, 44).getBytes();
		String pass = dbPassword.substring(44);
		System.out.println("pass from form = "+passFromForm);
		System.out.println("pass = "+ pass + " salt = " + saltString);
		
		
		if (pass.equals(codePassword(passFromForm, salt)[0])) {
			return true;
		}else {
			return false;
		}
	}*/	
	
}
