package utils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

/*
    Our simple static class that demonstrates how to create and decode JWTs.
 */
public class JWT {

    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String SECRET_KEY = System.getenv("BANKDB_SECRET_KEY");

    //Sample method to construct a JWT
    public static String create(String username) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long ttlMillis = 3600000;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId("JWT")
                .setIssuedAt(now)
                .setSubject("AuthenticationJWT")
                .claim("username", username)
                .setIssuer("Dewey Cheatem and Howe")
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decode(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
    
    /**
     * Just stub class - always returns false 
     * @param jwt JSON Web Token
     * @return false
     */
    public static boolean isExpired(String jwt) {
    	Claims claims = decode(jwt);
//    	System.out.println(claims.get("exp").getClass());
//    	long exp = Long.parseLong(claims.get("exp"));
//    	System.out.println("EXP >>> " + exp);
//    	long nowMillis = System.currentTimeMillis();
//    	System.out.println("NOW >>> " + nowMillis);
    	
    	return false;
    }

}
