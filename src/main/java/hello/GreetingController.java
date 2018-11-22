package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwt_generation.GenerateJWT;
 
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
        public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping(value = "/protected_greeting", method = RequestMethod.POST)
    public Object NoAccess(
            @RequestParam(value="name", defaultValue="World") String name, 
            @RequestHeader(value=SecurityConsts.HEADER_STRING, defaultValue="none") String auth
            ) {
        
        if(ValidateJWT.isValid(auth)) {//
        	
            return new Greeting(counter.incrementAndGet(), String.format(template, name));
            
        } else {
            
            return new NoAccess();
                     
        }
    }
    
    
    @RequestMapping(value = "/create_token", method = RequestMethod.POST)
    public GenerateJWT jwt (
            @RequestParam(value="subject") String subject,
            @RequestParam(value="duration") long duration,
            @RequestParam(value="name") String name,
            @RequestParam(value="audience") String audience,
            @RequestParam(value="issuer", defaultValue=SecurityConsts.ISS) String issuer
            
            ) {      
        
        return new GenerateJWT(name, issuer , subject, audience, duration*3600000);
    }
    
    
   
}