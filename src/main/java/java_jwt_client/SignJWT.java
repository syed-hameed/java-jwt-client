package java_jwt_client;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.security.KeyStore;
import java.util.HashMap;

public class SignJWT {

	public static void main(String[] args) {
		Key key =   getPrivateKey();
		
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put( "scope", "foo");
//		claims.put( "organization", "tomZNQb");

		String compactJws = Jwts.builder()
//		  .setSubject("Joe")
		  .signWith(SignatureAlgorithm.RS256, key).setId("ea042d68-ab61-428a-b543-3f247b8a565f").setClaims(claims)
		  .compact();
		System.out.println("Signed JWT is :"+ compactJws);
	}
	
	
	private static  Key getPrivateKey() {
        Key privateKey = null;

        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(SignJWT.class.getResourceAsStream("/mytest.jks"),
                    "mypass".toCharArray());
            KeyStore.ProtectionParameter entryPass = new KeyStore.PasswordProtection("mypass".toCharArray());
//            privateKey = ( (PrivateKeyEntry) keystore.getEntry("mytest", entryPass)).getPrivateKey();
            privateKey = keystore.getKey("mytest", "mypass".toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (privateKey == null) {
            System.err
                    .println("Failed to retrieve private key from mytest.jks");
            System.exit(-1);
        }

        return privateKey;
    }

}
