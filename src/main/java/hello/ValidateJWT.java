package hello;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class ValidateJWT {
	
/*	public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9."
        		+ "eyJzdWIiOiIxMjM0NTY3ODkiLCJuYW1lIjoiSm9obiIsImlhdCI6MTU0MjgzMTAwNSwiZXhwIjoxNTQyODQxMDA1LCJpc3MiOiJhdXRoMCIsImF1ZCI6ImV4dGVybmFsIn0."
        		+ "mubmquN4YMRDdeCwRZSX_qFyRy5jGzob7fW0VfSQQr8";
		isValid(token);
		
		
    }*/
	
	
	
	public static boolean isValid(String token) {
		
		if(token.startsWith("Bearer "))
            token = token.substring(7);
		
		
		try {
		   
			Algorithm algorithm = Algorithm.HMAC256(SecurityConsts.SECRET);
		    
			JWTVerifier verifier = JWT.require(algorithm)
		       // .withIssuer(SecurityConsts.ISS)
		        .build(); //Reusable verifier instance
		  
		    
			DecodedJWT jwt = verifier.verify(token);
		  
		   	logClaims(jwt);
			
			return true;
		
		} catch (JWTVerificationException exception){
		System.out.println(exception.getMessage());
			return false;

		}

	}
	
	
	
	 private static void logClaims(DecodedJWT jwt) {
	        
	        Map<String, Claim> claims = jwt.getClaims();
	        
	        StringBuffer out = new StringBuffer();
	        
	        for (Entry<String, Claim> pair : claims.entrySet()){
	            //iterate over the pairs
	            String value = pair.getValue().asString();
	            if(pair.getKey() == "exp" || pair.getKey() == "iat")
	                value = pair.getValue().asDate().toString();
	            
	            out.append("\n"+pair.getKey()+" "+value);            
	        }
	        
	        Logger logger = (Logger) LoggerFactory.getLogger(ValidateJWT.class);
	        logger.info(out.toString());
	    }
}