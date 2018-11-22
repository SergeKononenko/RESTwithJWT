package jwt_generation;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import hello.SecurityConsts;    

public class GenerateJWT {


	private String token;
	
	

	//Sample method to construct a JWT
	public GenerateJWT(String name, String iss, String subject, String aud, long ttlMillis) {
	 
	  	Date now = new Date();
		Date expiresAt = new Date();	
		
		
	    if (ttlMillis >= 0) {
	    long exp = now.getTime() + ttlMillis;
	    expiresAt = new Date(exp);  
	    }
	    
	    
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SecurityConsts.SECRET);
		    String token = JWT.create()
		    		.withSubject(subject)
		    		.withClaim("name", name)
		    		.withIssuedAt(now)
		    		.withExpiresAt(expiresAt)
		        .withIssuer(iss)
		        .withClaim("aud", aud)
		        .sign(algorithm);
		    
		    this.token = token;;
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
			this.token =  "no token";
		}
	}


	
	public String getToken() {
        return token;
    }
	
}

















